$(() => {
    new textareaMax();
})

export class textareaMax {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {


        //textarea 바이트 수 체크하는 함수
        function fn_checkByte(obj) {
            const maxByte = 1000; //최대 100바이트
            const text_val = obj.value; //입력한 문자
            const text_len = text_val.length; //입력한 문자수
            const text_id = obj.id; // textarea 고유 id값
            const targetIndex = text_id.substring(18)

            console.log("ttttttt1", text_id)
            let text_cnt=0;
            let totalByte = 0;
            for (let i = 0; i < text_len; i++) {
                const each_char = text_val.charAt(i);
                const uni_char = escape(each_char); //유니코드 형식으로 변환
                if (uni_char.length > 4) {
                    // 한글 : 2Byte
                    totalByte += 2;
                    if(totalByte <=1000) {
                        text_cnt += 1;
                    }
                } else {
                    // 영문,숫자,특수문자 : 1Byte
                    totalByte += 1;
                    if(totalByte <=1000) {
                        text_cnt += 1;
                    }
                }
            }
            console.log("dddddddddddddd",text_cnt)
            if (totalByte > maxByte) {
                console.log("tttttt",text_cnt)
                // document.getElementById(obj.id).value = sliceByByte(document.getElementById(obj.id).value)
                document.getElementById(obj.id).value = document.getElementById(obj.id).value.substring(0, text_cnt)
                document.getElementById("nowByte" + targetIndex).innerText = totalByte.toString();
                document.getElementById("nowByte" + targetIndex).style.color = "red";
                alert('최대 1000Byte까지만 입력가능합니다.');
            } else {
                document.getElementById("nowByte" + targetIndex).innerText = totalByte.toString();
                document.getElementById("nowByte" + targetIndex).style.color = "green";
            }
        }

        $(document).on('click', '.commentView', (e) => {
                if(e.target.classList[2] == 'reviewOpen'){
                    $(e.target).removeAttr("style");
                    e.target.classList.remove('reviewOpen');
                    e.target.classList.add('reviewClose');
                }else{
                    e.target.classList.remove('reviewClose');
                    e.target.classList.add('reviewOpen');
                    $(e.target).attr("style","display:-webkit-box; -webkit-line-clamp:2;line-height:20px; -webkit-box-orient:vertical; overflow: hidden; text-overflow: ellipsis;");
                }
            }

        )

        $(document).on('keyup', '.commentTextarea', (e) => {
            fn_checkByte(e.target)
        })

    }
}
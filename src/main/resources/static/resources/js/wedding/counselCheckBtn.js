$(() => {
    new counselCheckBtn();
})

export class counselCheckBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        $(document).on('click','.checkCounselBtn', (e) => {
            counselCheck(e.target)
        })

        function counselCheck(target){
            const counselCheckIdx = target.id.substring(15);
            let counselCheckBtn = document.querySelector(`#counselCheck`+counselCheckIdx);
            console.log("인덱스 체크 ",counselCheckIdx)

            const updateData = {
                pcounselingIdx: counselCheckIdx,
            }
                axios.post('/plannerCounsel/update', updateData)
                    .then((result) => {
                        console.log(result);
                        if(result.data > 0) {
                            console.log("수정성공");
                            counselCheckBtn.classList.remove("btn-secondary")
                            counselCheckBtn.classList.add("btn-outline-secondary")
                            counselCheckBtn.innerHTML = "상담완료"
                            counselCheckBtn.disabled = true;
                        }else{
                            console.log("수정실패");
                        }

                    })


        }
    }
}



$(() => {
    new reviewBtn();
})

export class reviewBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {


        $(document).on('click', '.counselBtn', (e) => {
            changeText(e.target)
        })


        function changeText(target) {
            let reviewBtn = document.querySelector(`#${target.id}`);
            console.log("타겟", reviewBtn)
            let targetIndex = reviewBtn.id.substring(9)
            console.log("인덱스", targetIndex)
            let star = document.getElementById("star" + targetIndex).value
            console.log("별점", star)
            let review = document.getElementById("textArea_byteLimit" + targetIndex).value
            console.log("후기", review)
            let planner = document.getElementById("u_idx" + targetIndex).textContent
            console.log("플래너번호", planner)
            console.log("클래스리스트", reviewBtn.classList)
            console.log("클래스체크", reviewBtn.classList[4])


            const text_val = review; //입력한 문자
            const text_len = text_val.length; //입력한 문자수

            let totalByte = 0;
            for (let i = 0; i < text_len; i++) {
                const each_char = text_val.charAt(i);
                const uni_char = escape(each_char); //유니코드 형식으로 변환
                if (uni_char.length > 4) {
                    // 한글 : 2Byte
                    totalByte += 2;

                } else {
                    // 영문,숫자,특수문자 : 1Byte
                    totalByte += 1;
                }
            }

            document.getElementById("nowByte" + targetIndex).innerText = totalByte.toString();
            document.getElementById("nowByte" + targetIndex).style.color = "green";


            let textChange = review;


            if (reviewBtn.classList[4] === `review_write`) {
                reviewBtn.classList.remove('review_write')
                reviewBtn.classList.add('review_submit')
                reviewBtn.innerHTML = `후기 등록`;
            } else if (reviewBtn.classList[4] === `review_open`) {
                reviewBtn.classList.remove('review_open')
                reviewBtn.classList.add('review_modify')
                reviewBtn.innerHTML = `후기 수정`
            } else if (reviewBtn.classList[4] === `review_submit` || reviewBtn.classList[4] === `review_modify`) {
                textChange = textChange.replaceAll(/(\n|\r\n)/g, "<br>");

                let reviewCnt={
                    preview: review,
                }

                let updateData = {
                    pgrade: star,
                    preview: textChange,
                    uidx: planner
                }

                if (updateData.pgrade != 0 && updateData.preview != "") {
                    axios.post('/myCounsel/previewCnt', reviewCnt)
                        .then((result) => {
                            console.log("글자수체크",result.data);
                            if(result.data <= 1000){
                                axios.post('/myCounsel/reviewWrite', updateData)
                                    .then((result) => {
                                        console.log(result);
                                        if (result.data > 0) {
                                            console.log("수정성공");
                                            reviewBtn.classList.remove('review_submit')
                                            reviewBtn.classList.remove('review_modify')
                                            reviewBtn.classList.add('review_open')
                                            reviewBtn.innerHTML = `후기 보기`
                                        } else {
                                            console.log("수정실패");
                                        }
                                    })
                            }else{
                                alert("입력가능한 후기를 초과하였습니다.")
                            }
                        })
                }else{
                    if (reviewBtn.classList[4] === `review_submit`) {
                        reviewBtn.classList.remove('review_submit')
                        reviewBtn.classList.add('review_write')
                        reviewBtn.innerHTML = `후기 작성`;
                    }else if(reviewBtn.classList[4] === `review_modify`){
                        reviewBtn.classList.remove('review_modify')
                        reviewBtn.classList.add('review_open')
                        reviewBtn.innerHTML = `후기 보기`
                    }
                    alert("후기와 별점을 입력해주세요")
                }
            }
        }
    }
}



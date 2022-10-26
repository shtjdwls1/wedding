$(() => {
    new reviewBtn();
})

export class reviewBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {


        $('.counselBtn').on('click', (e) => {
            changeText(e.target)
        })


        function changeText(target){
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
            console.log("클래스체크", reviewBtn.classList[5])

            let updateData = {
                pgrade: star,
                preview: review,
                uidx: planner
            }

            if (reviewBtn.classList[5] === `review_write`) {
                reviewBtn.classList.remove('review_write')
                reviewBtn.classList.add('review_submit')
                reviewBtn.innerHTML = `후기 등록`;
            } else if (reviewBtn.classList[5] === `review_open`) {
                reviewBtn.classList.remove('review_open')
                reviewBtn.classList.add('review_modify')
                reviewBtn.innerHTML = `후기 수정`
            } else if (reviewBtn.classList[5] === `review_submit` || reviewBtn.classList[5] === `review_modify`) {
                if (reviewBtn.classList[5] === `review_submit`) {
                    reviewBtn.classList.remove('review_submit')
                } else if (reviewBtn.classList[5] === `review_modify`) {
                    reviewBtn.classList.remove('review_modify')
                }
                reviewBtn.classList.add('review_open')
                reviewBtn.innerHTML = `후기 보기`

                axios.post('/myCounsel/reviewWrite', updateData)
                    .then((result) => {
                        console.log(result);
                        if(result.data > 0) {
                            console.log("수정성공");
                        }else{
                            console.log("수정실패");
                        }
                    })

            }

        }







    }
}



const changeText = (target) => {
    let reviewBtn = document.querySelector(`#${target.id}`);
    let targetIndex = reviewBtn.id.substring(9);
    let star = document.getElementById("star" + targetIndex).value
    let review = document.getElementById("textArea_byteLimit" + targetIndex).value
    let planner = document.getElementById("p_idx" + targetIndex).textContent
    console.log("동작확인", reviewBtn)
    console.log("동작확인2", reviewBtn.textContent)
    console.log("동작확인3", reviewBtn.classList)
    console.log("동작확인4", reviewBtn.classList[4])

    let updateData = {
        pgrade: star,
        preview: review,
        pidx: planner
    }
    if (reviewBtn.classList[4] === `review-write`) {
        reviewBtn.classList.remove('review-write')
        reviewBtn.classList.add('review-submit')
        reviewBtn.innerHTML = `후기 등록`;
    } else if (reviewBtn.classList[4] === `review-open`) {
        reviewBtn.classList.remove('review-open')
        reviewBtn.classList.add('review-modify')
        reviewBtn.innerHTML = `후기 수정`
    } else if (reviewBtn.classList[4] === `review-submit` || reviewBtn.classList[4] === `review-modify`) {
        if (reviewBtn.classList[4] === `review-submit`) {
            reviewBtn.classList.remove('review-submit')
        } else if (reviewBtn.classList[4] === `review-modify`) {
            reviewBtn.classList.remove('review-modify')
        }
        reviewBtn.classList.add('review-open')
        reviewBtn.innerHTML = `후기 보기`

        axios.post('/myCounsel/reviewWrite', updateData)
            .then((result) => {
                if(result == 1){
                    console.log("후기등록 성공");
                }else{
                    console.log("후기등록 실패");
                }

            })

    }

}


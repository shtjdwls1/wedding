const changeText = (target) => {
    let reviewBtn = document.querySelector(`#${target.id}`);
    let targetIndex = reviewBtn.id.substring(9)
    var star = document.getElementById("star" + targetIndex).value
    var review = document.getElementById("textArea_byteLimit" + targetIndex).value
    let planner = document.getElementById("p_idx" + targetIndex).textContent
    // let counselIdx = 4; // 테스트용 사용자 번호

    const counselIdx = sessionStorage.getItem('u_idx');

    console.log("tttttttttttttttttttttttt", counselIdx)
    let updateData = {
        pgrade: star,
        preview: review,
        pidx: planner
    }

    if (reviewBtn.textContent === `후기 작성`) {
        reviewBtn.innerHTML = `후기 등록`;
    } else if (reviewBtn.textContent === `후기 보기`) {
        reviewBtn.innerHTML = `후기 수정`
    } else if (reviewBtn.textContent === `후기 등록` || reviewBtn.textContent === `후기 수정`) {
        reviewBtn.innerHTML = `후기 보기`

        axios.post('/myCounsel/reviewWrite',updateData)
            .then((result) => {
            console.log(result);
        })

    }

}


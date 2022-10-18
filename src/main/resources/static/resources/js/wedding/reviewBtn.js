const changeText = (target) => {
    let reviewBtn = document.querySelector(`#${target.id}`);
    let targetIndex = reviewBtn.id.substring(9)
    let star = document.getElementById("star" + targetIndex).value
    let review = document.getElementById("textArea_byteLimit" + targetIndex).value
    let planner = document.getElementById("p_idx" + targetIndex).textContent
    console.log("test1======" + star);
    console.log("test2======" + review);
    console.log("test3======" + planner);
    var updateData = {
        'pGrade': star,
        'pReview': review,
        'pIdx': planner
    }

    if (reviewBtn.textContent === `후기 작성`) {
        reviewBtn.innerHTML = `후기 등록`;
        axios({
            url: '/myCounsel/reviewWrite/4',
            method: 'post',
            data: JSON.stringify(updateData)
        }).then(function (data) {

            var rawJsonData = JSON.parse(data);

            console.log("------" + rawJsonData);

        })
    } else if (reviewBtn.textContent === `후기 등록` || reviewBtn.textContent === `후기 수정`) {
        reviewBtn.innerHTML = `후기 보기`

        axios({
            url: '/myCounsel/reviewWrite/4',
            method: 'post',
            data: JSON.stringify(updateData)
        }).then(function (data) {

            var rawJsonData = JSON.parse(data);

            console.log("------" + rawJsonData);

        })
    } else if (reviewBtn.textContent === `후기 보기`) {
        reviewBtn.innerHTML = `후기 수정`
    }

}


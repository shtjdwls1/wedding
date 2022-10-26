$(() => {
    new reviewBtn();
})

export class reviewBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        const review_write = document.querySelector('.review_write');
        const review_open = document.querySelector('.review_open');
        const confirm_wait = document.querySelector('.confirm_wait');


        review_write.addEventListener('click', changeText)

        function changeText(target){
            let check = review_write.id
            console.log("동작확인1", check)
            console.log("동작확인1", target.id)
            let reviewBtn = document.querySelector(`#${target.id}`);
            console.log("동작확인2", reviewBtn)

            let targetIndex = reviewBtn.id
            let star = document.getElementById("star" + targetIndex).value
            let review = document.getElementById("textArea_byteLimit" + targetIndex).value
            let planner = document.getElementById("u_idx" + targetIndex).textContent
            console.log("동작확인", reviewBtn)
            console.log("동작확인2", reviewBtn.textContent)
            console.log("동작확인3", reviewBtn.classList)
            console.log("동작확인4", reviewBtn.classList[4])

            let updateData = {
                pgrade: star,
                preview: review,
                pidx: planner
            }


            if (counselBtn.classList[4] === `review_write`) {
                counselBtn.classList.remove('review_write')
                counselBtn.classList.add('review_submit')
                counselBtn.innerHTML = `후기 등록`;
            } else if (counselBtn.classList[4] === `review_open`) {
                counselBtn.classList.remove('review_open')
                counselBtn.classList.add('review_modify')
                counselBtn.innerHTML = `후기 수정`
            } else if (counselBtn.classList[4] === `review_submit` || counselBtn.classList[4] === `review_modify`) {
                if (counselBtn.classList[4] === `review_submit`) {
                    counselBtn.classList.remove('review_submit')
                } else if (counselBtn.classList[4] === `review_modify`) {
                    counselBtn.classList.remove('review_modify')
                }
                counselBtn.classList.add('review_open')
                counselBtn.innerHTML = `후기 보기`
                //
                // axios.post('/myCounsel/reviewWrite', updateData)
                //     .then((result) => {
                //         console.log(result);
                //     })

    }

        }


    }
}


//
//
//
//
// $(() => {
//     new reviewBtn();
// })
//
// export class reviewBtn {
//     constructor() {
//         this.eventBindgin()
//     }
//
//     eventBindgin() {
//
//         const counselBtn = document.querySelector('.counselBtn');
//         const review_write = document.querySelector('.review_write');
//         const review_open = document.querySelector('.review_open');
//         const confirm_wait = document.querySelector('.confirm_wait');
//
//
//         review_write.addEventListener('click', changeText)
//
//         function changeText(target){
//             let reviewBtn = document.querySelector(`#${target.id}`);
//             let targetIndex = reviewBtn.id
//             let star = document.getElementById("star" + targetIndex).value
//             let review = document.getElementById("textArea_byteLimit" + targetIndex).value
//             let planner = document.getElementById("p_idx" + targetIndex).textContent
//             console.log("동작확인", reviewBtn)
//             console.log("동작확인2", reviewBtn.textContent)
//             console.log("동작확인3", reviewBtn.classList)
//             console.log("동작확인4", reviewBtn.classList[5])
//
//             let updateData = {
//                 pgrade: star,
//                 preview: review,
//                 pidx: planner
//             }
//             if (reviewBtn.classList[5] === `review-write`) {
//                 reviewBtn.classList.remove('review-write')
//                 reviewBtn.classList.add('review-submit')
//                 reviewBtn.innerHTML = `후기 등록`;
//             } else if (reviewBtn.classList[5] === `review-open`) {
//                 reviewBtn.classList.remove('review-open')
//                 reviewBtn.classList.add('review-modify')
//                 reviewBtn.innerHTML = `후기 수정`
//             } else if (reviewBtn.classList[5] === `review-submit` || reviewBtn.classList[5] === `review-modify`) {
//                 if (reviewBtn.classList[5] === `review-submit`) {
//                     reviewBtn.classList.remove('review-submit')
//                 } else if (reviewBtn.classList[5] === `review-modify`) {
//                     reviewBtn.classList.remove('review-modify')
//                 }
//                 reviewBtn.classList.add('review-open')
//                 reviewBtn.innerHTML = `후기 보기`
//
//                 axios.post('/myCounsel/reviewWrite', updateData)
//                     .then((result) => {
//                         console.log(result);
//                     })
//
//             }
//
//         }
//
//
//     }
// }
//
//

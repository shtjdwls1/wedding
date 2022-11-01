$(() => {
    new weddingDetail();
})

export class weddingDetail {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        // const hallTimeModal = document.querySelector('.hallTimeModal');
        // const checkTimeInput = document.querySelector(".checkTimeInput")
        // let test

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
//         function controllHallModal(hallName) {
//             if ($('.hallTimeModal').hasClass('hidden')) {
//                 $('.hallTimeModal').removeClass('hidden')
//             } else {
//                 $('.hallTimeModal').addClass('hidden')
//             }
//             test = hallName
//         }

        $('.timeBtn').on('click', (e) => {
            console.log(e.target.value)
            controllHallModal(e.target.value)
        })
        $('.modalCloseBtn').on('click', () => {
            controllHallModal();
        })

//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === hallTimeModal ? hallTimeModal.classList.add('hidden') : false
        })


// 옵션 클릭시 색 변경, 체크 추가
//         const hallTimeOption = document.getElementsByClassName("hallTimeOption");
//
//         function handleClickHallTime(event) {
//             console.log(event.target);
//             // console.log(this);
//             // 콘솔창을 보면 둘다 동일한 값이 나온다
//
//             console.log("event.target.classList", event.target.classList);
//
//             if (event.target.classList[1] === "clicked") {
//                 event.target.classList.remove("clicked");
//
//             } else {
//                 for (var i = 0; i < hallTimeOption.length; i++) {
//                     hallTimeOption[i].classList.remove("clicked");
//                 }
//
//                 event.target.classList.add("clicked");
//                 console.log("누른거!" + event.target.textContent)
//                 console.log(test)
//                 checkTimeInput.innerHTML = `<div>홀 선택 : ${test}</div><div>${event.target.textContent}</div>`
//                 controllHallModal();
//
//             }
//         }
//
//
//         function init() {
//             for (var i = 0; i < hallTimeOption.length; i++) {
//                 hallTimeOption[i].addEventListener("click", handleClickHallTime);
//             }
//
//         }
//
//         init();


    }
}






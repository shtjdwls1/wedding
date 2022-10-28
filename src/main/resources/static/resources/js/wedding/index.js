$(() => {
    new index();
})

export class index {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        const clasifyBtn = document.querySelector('.clasifyBtn');
        const locationBtn = document.querySelector('.locationBtn')
        const clasifyModal = document.querySelector('.clasifyModal');
        const dateModal = document.querySelector('.dateModal')

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
        function controllClasifyModal() {
            if ($('.clasifyModal').hasClass('hidden')) {
                $('.clasifyModal').removeClass('hidden')
            } else {
                $('.clasifyModal').addClass('hidden')
            }
        }

        $('.clasifyBtn').on('click', () => {
            controllClasifyModal()
        })

        $('.modalCloseBtn').on('click', () => {
            if ($('.clasifyModal').hasClass('hidden') == false) {
                $('.clasifyModal').addClass('hidden')
            }
            if ($('.locationModal').hasClass('hidden') == false) {
                $('.locationModal').addClass('hidden')
            }
            if ($('.sortModal').hasClass('hidden') == false) {
                $('.sortModal').addClass('hidden')
            }
        })

//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === clasifyModal ? clasifyModal.classList.add('hidden') : false
        })


// 옵션 클릭시 색 변경, 체크 추가
        const modalOption = document.getElementsByClassName("modalOption");
        const clasifyOption = document.getElementsByClassName("clasifyOption");
        const locationOption = document.getElementsByClassName("locationOption")

        function handleClickClasify(event) {
            console.log(event.target);
            // console.log(this);
            // 콘솔창을 보면 둘다 동일한 값이 나온다

            console.log("event.target.classList", event.target.classList);

            if (event.target.classList[1] === "clicked") {
                event.target.classList.remove("clicked");

            } else {
                for (var i = 0; i < clasifyOption.length; i++) {
                    clasifyOption[i].classList.remove("clicked");
                }

                event.target.classList.add("clicked");
                console.log("누른거!" + event.target.textContent)

                clasifyBtn.value = event.target.textContent;
                controllClasifyModal();

            }
        }


        function handleClickLocation(event) {
            console.log(event.target);
            // console.log(this);
            // 콘솔창을 보면 둘다 동일한 값이 나온다

            console.log("event.target.classList", event.target.classList);

            if (event.target.classList[1] === "clicked") {
                event.target.classList.remove("clicked");

            } else {
                for (var i = 0; i < locationOption.length; i++) {
                    locationOption[i].classList.remove("clicked");
                }

                event.target.classList.add("clicked");
                console.log("누른거!" + event.target.textContent)

                locationBtn.value = event.target.textContent;
                controllLocationModal();

            }
        }


        function init() {
            for (var i = 0; i < clasifyOption.length; i++) {
                clasifyOption[i].addEventListener("click", handleClickClasify);
            }
            for (var i = 0; i < locationOption.length; i++) {
                locationOption[i].addEventListener("click", handleClickLocation);
            }
        }

        init();

//지역 모달
        const locationModal = document.querySelector('.locationModal')

// 지역 선택 모달 창 나타나기, 사라지기
        function controllLocationModal() {
            if ($('.locationModal').hasClass('hidden')) {
                $('.locationModal').removeClass('hidden')
            } else {
                $('.locationModal').addClass('hidden')
            }
        }

        $('.locationBtn').on('click', () => {
            controllLocationModal()
        })
        // $('.locationModalCloseBtn').on('click', () => {
        //     controllLocationModal()
        // })


//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === locationModal ? locationModal.classList.add('hidden') : false
        })


//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === dateModal ? dateModal.classList.add('hidden') : false
        })

//모비 스크롤


        mobiscroll.datepicker('#date', {
            controls: ['date'],
            touchUi: true,
            locale: mobiscroll.localeKo,
            dateFormat: 'YYYY-MM-DD',
            display: 'bottom'
        })

        mobiscroll.datepicker('.timeInput', {
            controls: ['time'],
            timeFormat: 'HH:mm',
            touchUi: true
        })

// const hoverSec = document.querySelectorAll('.hoverSec')
//
// $(document).ready(function () {
//     $(".hoverSec").mouseover(function () {
//         $(this).addClass('hidden')
//     });
//     $(".hoverSec").mouseout(function () {
//         $(this).remove('hidden')
//         console.log('됨')
//     });
// })

    }
}



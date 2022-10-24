const classifyModal = document.querySelector('.clasifyModal');
const clasifyInput = document.querySelector("#clasify")
const locationInput = document.querySelector('#checkCity')

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
function controllClasifyModal() {
    classifyModal.classList.toggle('hidden')
}

//외부 클릭시 모달창 닫기
window.addEventListener('click', (e) => {
    e.target === classifyModal ? classifyModal.classList.add('hidden') : false
})


// 옵션 클릭시 색 변경, 체크 추가
const modalOption1 = document.getElementsByClassName("modalOption");
const clasifyOption1 = document.getElementsByClassName("clasifyOption");
const locationOption1 = document.getElementsByClassName("locationOption")

function handleClickClasify(event) {
    console.log(event.target);
    // console.log(this);
    // 콘솔창을 보면 둘다 동일한 값이 나온다

    console.log("event.target.classList", event.target.classList);

    if (event.target.classList[1] === "clicked") {
        event.target.classList.remove("clicked");

    } else {
        for (var i = 0; i < clasifyOption1.length; i++) {
            clasifyOption1[i].classList.remove("clicked");
        }

        event.target.classList.add("clicked");
        console.log("누른거!" + event.target.textContent)

        clasifyInput.value = event.target.textContent;
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
        for (var i = 0; i < locationOption1.length; i++) {
            locationOption1[i].classList.remove("clicked");
        }

        event.target.classList.add("clicked");
        console.log("누른거!" + event.target.textContent)

        locationInput.value = event.target.textContent;
        controllLocationModal();

    }
}


function init() {
    for (var i = 0; i < clasifyOption1.length; i++) {
        clasifyOption1[i].addEventListener("click", handleClickClasify);
    }
    for (var i = 0; i < locationOption1.length; i++) {
        locationOption1[i].addEventListener("click", handleClickLocation);
    }
}

init();

//지역 모달
const locationModal1 = document.querySelector('.locationModal')

// 지역 선택 모달 창 나타나기, 사라지기
function controllLocationModal() {
    locationModal1.classList.toggle('hidden')
}

//외부 클릭시 모달창 닫기
window.addEventListener('click', (e) => {
    e.target === locationModal1 ? locationModal1.classList.add('hidden') : false
})
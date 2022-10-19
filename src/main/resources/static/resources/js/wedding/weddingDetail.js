const clasifyBtn = document.getElementById('clasifyBtn');
const clasifyModal = document.querySelector('.clasifyModal');
const clasifyInput = document.querySelector(".checkTimeInput")
const locationInput = document.querySelector('#checkCity')
let test

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
function controllClasifyModal(hallName) {
    clasifyModal.classList.toggle('hidden')
    test = hallName
}

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
        console.log(test)
        clasifyInput.innerHTML = `<div>홀 선택 : ${test}</div><div>${event.target.textContent}</div>`
        controllClasifyModal();

    }
}


function init() {
    for (var i = 0; i < clasifyOption.length; i++) {
        clasifyOption[i].addEventListener("click", handleClickClasify);
    }

}

init();



const detailBtn = document.querySelector(".detailBtn");
const commentBtn = document.querySelector(".commentBtn");
const detailSec = document.querySelector(".detailSec");
const commentSec = document.querySelector(".commentSec");
const commentTop = document.querySelector(".commentTop");

detailBtn.onclick = function () {
    detailSec.classList.remove("hidden");
    commentSec.classList.add("hidden");
    commentTop.classList.add("hidden");
    window.scrollTo({top: 0, behavior: "smooth"});
}

commentBtn.onclick = function () {
    commentSec.classList.remove("hidden");
    commentTop.classList.remove("hidden");
    detailSec.classList.add("hidden");
    window.scrollTo({top: 0, behavior: "smooth"});

}

// 모달

const sortModal = document.querySelector('.sortModal');
const sortBtn = document.querySelector(".sortBtn")

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
function controllClasifyModal() {
    sortModal.classList.toggle('hidden')
}

//외부 클릭시 모달창 닫기
window.addEventListener('click', (e) => {
    e.target === sortModal ? sortModal.classList.add('hidden') : false
})


// 옵션 클릭시 색 변경, 체크 추가
const sortOption = document.getElementsByClassName("sortOption");

function handleClickClasify(event) {
    console.log(event.target);
    // console.log(this);
    // 콘솔창을 보면 둘다 동일한 값이 나온다

    console.log("event.target.classList", event.target.classList);

    if (event.target.classList[1] === "clicked") {
        event.target.classList.remove("clicked");

    } else {
        for (var i = 0; i < sortOption.length; i++) {
            sortOption[i].classList.remove("clicked");
        }

        event.target.classList.add("clicked");
        console.log("누른거!" + event.target.textContent)
        sortBtn.innerText = event.target.textContent;
        controllClasifyModal();

    }
}

function init() {
    for (var i = 0; i < sortOption.length; i++) {
        sortOption[i].addEventListener("click", handleClickClasify);
    }
}

init();
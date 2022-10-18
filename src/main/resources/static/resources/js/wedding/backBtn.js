const backBtn = document.querySelector('.backBtn')

function goBack() {
    window.history.back();
}

backBtn.addEventListener("click", goBack)
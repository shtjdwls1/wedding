const modal = document.querySelector(".modal");
const imgs = document.querySelectorAll(".img");
const modal_img = document.querySelector(".modal_content");
const span = document.querySelector(".close");

// img.addEventListener('click', (e) => {
//     console.log(this)
//     console.log(e.target)
//     console.log(e.currentTarget)
//     modalDisplay("block");
//     modal_img.src = img.src;
// });
span.addEventListener('click', () => {
    modalDisplay("none");
});
modal.addEventListener('click', () => {
    modalDisplay("none");
});

function modalDisplay(text) {
    modal.style.display = text;
}

function initModalImg() {

    imgs.forEach(function (img) {
        img.addEventListener('click', (e) => {
            console.log(e.target.src)
            console.log(e.currentTarget.src)
            modalDisplay("block");
            modal_img.src = e.target.src;
        });
    })

}

initModalImg();
$(() => {
    new backBtn();
})

export class backBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        const backBtn = document.querySelector('.backBtn')

        function goBack() {
            window.history.back();
        }

        backBtn.addEventListener("click", goBack)
    }
}


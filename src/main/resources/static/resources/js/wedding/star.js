$(() => {
    new drawStar();
})

export class drawStar {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        const drawStar = (target) => {
            document.querySelector(`.star .${target.id}`).style.width = `${target.value * 10}%`;
        }
    }
}




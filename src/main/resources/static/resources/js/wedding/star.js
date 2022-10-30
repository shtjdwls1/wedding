$(() => {
    new drawStar();
})

export class drawStar {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        $(document).on('change','.star', (e) => {
            drawStar(e)
        })

        function drawStar(e) {
            document.querySelector(`.star .${e.target.id}`).style.width = `${e.target.value * 10}%`;
        }


    }
}




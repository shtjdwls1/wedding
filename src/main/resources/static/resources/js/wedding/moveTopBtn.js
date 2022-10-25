$(() => {
    new moveTopBtn();
})

export class moveTopBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {
        $(function () {
            $(window).scroll(function () {
                if ($(this).scrollTop() > 500) {
                    $('#moveTopBtn').fadeIn();
                } else {
                    $('#moveTopBtn').fadeOut();
                }
            });

            $('#moveTopBtn').click(function () {
                $('html, body').animate({
                    scrollTop: 0
                }, 400);
                return false;
            })
        })
    }
}
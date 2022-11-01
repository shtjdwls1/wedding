//망한듯 ㅋ

$(() => {
    new WedImgSlide();
})

export class WedImgSlide {
    constructor() {
        this.eventBinding()

    }

    eventBinding() {
        const slider = $('.slider');
        const sliders = document.querySelectorAll('.slider')
        const wrapper = $('.sliderWrapper');
        const items = $('.sliderItems');
        const item = $('.sliderItem');
        const next = $('.sliderNext');
        const prev = $('.sliderPrev');
        console.log("아이템 랭스", item.length);
        console.log("slieder 랭스", sliders.length);
        const itemCount = item.length / sliders.length - 2;
        let startX = 0;         //mousedown시 위치
        let moveX = 0;         //움직인 정도
        let currentIdx = 0;    //현재 위치(index)
        let positions = [];

        const jsItmes = document.querySelectorAll('.sliderItems')
        let itemsWidth = [];


        function catchChildNum() {
            let childN = []
            for (let i = 0; i < jsItmes.length; i++) {
                let num = (jsItmes[i].childNodes.length - 1) / 2
                jsItmes[i].style.width = (num * 100 + '%')
                childN.push(num)
            }
            itemsWidth = childN;
        }


        function initializeData() {
            catchChildNum()
            const isActive = items.hasClass('active');
            if (isActive) items.removeClass('active');
            const width = wrapper[1].clientWidth;
            const interval = item[1].clientWidth;
            const margin = (width - interval) / 2
            const initX = Math.floor((interval - margin) * -1);
            let pos = [];
            for (let i = 0; i < itemCount; i++) {
                pos.push(initX - interval * i);
            }
            positions = pos;
            // items.css('width', (itemCount + 1) * 100 + '%')
            items.css('left', positions[currentIdx] + 'px')
            slider.css('visibility', 'visible')
        }

        // window.addEventListener('resize', initializeData);
        $(window).resize(initializeData())
        // window.addEventListener('load',  initializeData);
        // $(window).load(initializeData())

// btn click event
        next.on("click", (e) => {
            console.log("넥스트클릭")
            let target = $(e.target.parentNode.childNodes[1].childNodes[1])
            let target2 = (e.target.parentNode.childNodes[1].childNodes[1].childNodes.length - 1) / 2
            if (currentIdx == target2 - 3) return;
            const isActive = target.hasClass('active');
            if (!isActive) target.addClass('active');
            currentIdx = currentIdx + 1;
            target.css('left', positions[currentIdx] + 'px')
        });
        prev.on('click', (e) => {
            console.log("프리브클릭")
            let target = $(e.target.parentNode.childNodes[1].childNodes[1])
            if (currentIdx === 0) return;
            const isActive = target.hasClass('active');
            if (!isActive) target.addClass('active');
            currentIdx = currentIdx - 1;
            target.css('left', positions[currentIdx] + 'px')
        });


        // wrapper.on('mousedown', (e) => {
        //     console.log('마우스다운')
        //     const rect = wrapper.offset();
        //     console.log(rect)
        //     startX = e.clientX - rect.left;
        //     const isActive = items.hasClass('active');
        //     if (!isActive) items.addClass('active');
        //     items.on('mousemove', onMouseMove);
        //     document.onmouseup = (e) => {
        //         if (wrapper.hasClass('active')) wrapper.removeClass('active');
        //         items.on('mousemove', onMouseMove);
        //         document.onmouseup = null;
        //         if (moveX > -70 && moveX <= 70) {
        //             //   만약 -70~70이면 초기위치로 이동
        //             return items.css('left', positions[currentIdx] + 'px');
        //         }
        //         if (moveX > 0 && currentIdx > 0) {
        //             currentIdx = currentIdx - 1;
        //             items.css('left', positions[currentIdx] + 'px');
        //         }
        //         if (moveX < 0 && currentIdx < itemCount - 1) {
        //             currentIdx = currentIdx + 1;
        //             items.css('left', positions[currentIdx] + 'px');
        //         }
        //
        //     }
        // })
        //
        // function onMouseMove(e) {
        //     if (!wrapper.hasClass('active')) wrapper.addClass('active');
        //     const rect = wrapper.offset();
        //     moveX = e.clientX - rect.left - startX;
        //     const left = positions[currentIdx] + moveX;
        //     if (currentIdx === 0 && moveX > 0) return;
        //     else if (currentIdx === itemCount - 1 && moveX < 0) return;
        //     items.css('left', left + 'px');
        // }
    }
}




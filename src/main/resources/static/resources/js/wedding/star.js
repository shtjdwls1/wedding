const drawStar = (target) => {
    document.querySelector(`.star .${target.id}`).style.width = `${target.value * 10}%`;
}
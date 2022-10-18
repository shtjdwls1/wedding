const drawStar = (target) => {
    console.log(target);
    console.log(this)
    document.querySelector(`.star span`).style.width = `${target.value * 10}%`;
}



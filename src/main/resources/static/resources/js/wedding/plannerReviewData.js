$(() => {
    new plannerReviewData();
})

export class plannerReviewData {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        const param = new URLSearchParams(location.search)
        let sortCnt = parseInt(param.get("offset"));

        console.log("offset", param.get("offset"));
        console.log("columnData", param.get("columnData"));
        console.log("sortData", param.get("sortData"));

        //스크롤 이벤트발생
        $(window).on('scroll', (e) => {
            infiniteScroll()
        })

        function infiniteScroll() {
            // 스크롤 위치 비교
            console.log("윈도우 스크롤 탑", $(window).scrollTop())
            console.log("문서의 높이", $(document).height())
            console.log("윈도우 높이", $(window).height())
            if ($(window).scrollTop() >= $(document).height() - $(window).height()) {
                //데이터 요청
                sortCnt += 10;

                let newSortData = {
                    offset: sortCnt,
                    columnData: param.get("columnData"),
                    sortData: param.get("sortData"),
                }

                axios.post("/plannerReviewData", newSortData)
                    .then((result) => {
                        // console.log("result", result)
                        let reviewHtml = "";
                        console.log("result.data[1]", result.data[1])
                        for (let i = 0; i < result.data.length; i++) {
                            reviewHtml += `<div class="comment">
                        <div class="outerStar mx-3" style="justify-content: flex-start">
                        <div class="mt-1 mx-1">${result.data[i].uname}</div>
                        <!-- width 값 에 db값 * 10 % 해서 별 출력-->
                        <span class="star text" style="font-size: 1rem">★★★★★<span
                                style='width:'+${result.data[i].pgrade}*10+'%'>★★★★★</span>
                        <input max="10" min="0" name="star1" step="1" type="range" readonly
                                value=${result.data[i].pgrade}>
                        </span>
                    </div>
                    <div class="plannerIntro mx-3 mb-2">
                        <div class="commentView px-2">
                            ${result.data[i].preview}
                        </div>
                    </div>
                </div>`;

                        }
                        console.log("html>>>>>", reviewHtml)

                    })
            }
        }
    }
}


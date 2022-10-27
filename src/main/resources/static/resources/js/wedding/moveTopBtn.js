$(() => {
    new moveTopBtn();
})

export class moveTopBtn {
    constructor() {
        this.eventBinding()
    }

    eventBinding() {
        $(function () {

            const param = new URLSearchParams(location.search)
            let sortCnt = parseInt(param.get("offset"));
            let ck = param.get("ck");


            $(window).scroll(function () {
                if ($(this).scrollTop() > 100) {
                    $('#moveTopBtn').fadeIn();
                } else {
                    $('#moveTopBtn').fadeOut();
                }

                infiniteScroll()

            });

            function infiniteScroll() {
                // 스크롤 위치 비교
                if ($(window).scrollTop() >= $(document).height() - $(window).height()) {
                    //데이터 요청
                    sortCnt += 10;
                    // ck로 각 무한스크롤 페이지 판단
                    if (ck == "pr") {
                        let newSortData = {
                            offset: sortCnt,
                            columnData: param.get("columnData"),
                            sortData: param.get("sortData"),
                        }


                        axios.post("/plannerReviewData", newSortData)
                            .then((result) => {
                                console.log("result", result)
                                let reviewHtml = "";
                                console.log("result.data[1]", result.data[1])
                                for (let i = 0; i < result.data.length; i++) {
                                    reviewHtml +=
                                        `<div class="comment">
                        <div class="outerStar mx-3" style="justify-content: flex-start">
                        <div class="mt-1 mx-1">${result.data[i].uname}</div>
                        <!-- width 값 에 db값 * 10 % 해서 별 출력-->
                        <span class="star text" style="font-size: 1rem">★★★★★<span
                                style="width:${result.data[i].pgrade * 10}%">★★★★★</span>
                        <input max="10" min="0" name="star1" step="1" type="range" readonly
                                value=${result.data[i].pgrade}>
                        </span>
                    </div>
                    <div class="plannerIntro mx-3 mb-2">
                        <div class="commentView px-2">
                            ${result.data[i].preview}
                        </div>
                    </div>
                </div>`
                                }
                                $('.plannerReview').append(reviewHtml)
                            })
                    }




                    // ck로 각 무한스크롤 페이지 판단
                    if (ck == "mc") {
                        let newSortData = {
                            offset: sortCnt,
                        }


                        axios.post("/myCounselData", newSortData)
                            .then((result) => {
                                console.log("result", result)
                                let reviewHtml = "";
                                console.log("result.data[1]", result.data[1])
//                                 for (let i = 0; i < result.data.length; i++) {
//                                     reviewHtml +=`
// <!-- 상담 카드 -->
// <div class="outer  mt-2 mx-3">
// <div class="plannerCard">
// <div class="Photosec mx-2 my-2">
// <div>
// <img class="plannerPhoto mt-2" src="/imgs/PlannerPhoto.jpg">
// </div>
// </div>
// <div class="plannerInfo mx-2">
// <table class="table table-borderless my-2">
// <tbody>
// <tr hidden>
// <th scope="row">인덱스번호</th>
// <td th:id="'u_idx'+${myCounselStat.index}">${result.data[i].uIdx}</td>
// </tr>
// <tr>
// <th scope="row">플래너사진이름</th>
// <td th:id="'photoName'+${myCounselStat.index}" th:text="${myCounsel.getPImgName()}">플래너사진이름</td>
// </tr>
// <tr>
// <th scope="row">플래너사진경로</th>
// <td th:id="'photoPath'+${myCounselStat.index}" th:text="${myCounsel.getPImgPath()}">플래너사진경로</td>
// </tr>
// <tr>
// <th scope="row">플래너사진타입</th>
// <td th:id="'photoType'+${myCounselStat.index}" th:text="${myCounsel.getPImgType()}">플래너사진타입</td>
// </tr>
// <tr>
// <th scope="row">상담사명</th>
// <td th:Text="${myCounsel.getUName()}">김땡땡</td>
// </tr>
// <tr>
// <th scope="row">상담일자</th>
// <td th:Text="${myCounsel.getCounselDate()}">2022-12-31</td>
//
// </tr>
// <tr>
// <th scope="row">견적</th>
// <td th:Text="${myCounsel.getPPrice()} + 원">400만~</td>
// </tr>
// <tr>
// <th scope="row">연락처</th>
// <td th:Text="${myCounsel.getUTel()}">010-9876-5432</td>
// </tr>
// <tr>
// <th scope="row"></th>
// <td th:if="${myCounsel.getPCounselingCk() == 'T'} and ( ${#strings.isEmpty(myCounsel.getPReview())} or ${#strings.isEmpty(myCounsel.getPGrade())})">
// <button aria-controls="collapseExample" aria-expanded="false"
//         class="btn btn-sm btn-light border-dark counselBtn review_write"
//         data-bs-toggle="collapse"
//         th:data-bs-target="'#collapseExample'+ ${myCounselStat.index}"
//         th:id="'reviewBtn'+${myCounselStat.index}" type="button">후기 작성
// </button>
// </td>
// <td th:if="${myCounsel.getPCounselingCk() == 'T'} and ${not #strings.isEmpty(myCounsel.getPReview())} and ${not #strings.isEmpty(myCounsel.getPGrade())}">
// <button aria-controls="collapseExample" aria-expanded="false"
//         class="btn btn-sm btn-light border-dark counselBtn review_open"
//         data-bs-toggle="collapse"
//         th:data-bs-target="'#collapseExample'+ ${myCounselStat.index}"
//         th:id="'reviewBtn'+${myCounselStat.index}" type="button">후기 보기
// </button>
// </td>
//
// <td th:unless="${myCounsel.getPCounselingCk() == 'T'}">
// <button aria-controls="collapseExample" aria-expanded="false"
//         class="btn btn-sm btn-light border-dark counselBtn confirm_wait"
//         data-bs-toggle="collapse"
//         disabled
//         th:data-bs-target="'#collapseExample'+ ${myCounselStat.index}"
//         th:id="'reviewBtn'+${myCounselStat.index}"
//         type="button">
// 확인 대기
// </button>
// </td>
// </tr>
// </tbody>
// </table>
// </div>
// </div>
// <div class="commentSection mx-3">
//
// <form class="collapse" th:id="'collapseExample'+ ${myCounselStat.index}">
// <!--별점기능-->
// <div class="outerStar">
// <span class="star text">
//   ★★★★★
//   <span th:class="'star'+${myCounselStat.index}"
//         th:style="'width:'+${myCounsel.getPGrade()}*10+'%'">★★★★★</span>
//   <input class="star" max="10" min="0" step="1" th:id="'star'+${myCounselStat.index}"
//          th:value="${myCounsel.getPGrade()}" type="range">
// </span>
// </div>
// <!--여기까지-->
// <table class="table border">
// <tr>
// <th style="vertical-align: middle;">
// TextArea<br/>
// <sup>(<span th:id="'nowByte'+ ${myCounselStat.index}">0</span>/1000bytes)</sup>
// </th>
// <td>
// <form id="frm_textArea" name="frm_textArea">
//                 <textarea class="form-control commentTextarea"
//                           name="textArea_byteLimit"
//
//                           rows="10"
//                           th:id="'textArea_byteLimit'+ ${myCounselStat.index}"
//                           th:text="${myCounsel.getPReview()}"></textarea>
// </form>
// </td>
// </tr>
// </table>
// </form>
// </div>
// </div>`
//                                 }
//                                 $('.myCounsel').append(reviewHtml)
                            })
                    }













                }
            }


            $('#moveTopBtn').click(function () {
                $('html, body').animate({
                    scrollTop: 0
                }, 300);
                return false;
            })
        })
    }
}
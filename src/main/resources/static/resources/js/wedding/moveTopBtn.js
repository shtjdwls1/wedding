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
            let sortData = param.get("sortData");
            let columnData = param.get("columnData");
            let ck = param.get("ck");
            let endCk = false;

            // 첫페이지 스크롤바가 생성되지 않았을 경우
            if ($("body").height() < $(window).height()) {
                infiniteScroll()
            }

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
                            columnData: columnData,
                            sortData: sortData,
                        }
                        if(endCk != true) {
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
                                                    <div style="color: dimgrey; margin-left: auto">${result.data[i].counselDate}</div>
                                                </div>
                                            <div class="plannerIntro mx-3 mb-2">
                                                <div style="display:-webkit-box; -webkit-line-clamp:2;line-height:20px; -webkit-box-orient:vertical;
                                                    overflow: hidden; text-overflow: ellipsis;" class="commentView px-2 reviewOpen">${result.data[i].preview}</div>
                                            </div>
                                        </div>`
                                    }
                                    $('.plannerReview').append(reviewHtml)
                                    if (result.data.length < 10) {
                                        endCk = true;
                                    }
                                })
                        }
                    }
                    // ck로 각 무한스크롤 페이지 판단
                    if (ck == "mc") {
                        let newSortData = {
                            offset: sortCnt,
                        }
                        if(endCk != true) {
                        axios.post("/myCounselData", newSortData)
                            .then((result) => {
                                console.log("result", result)
                                let reviewHtml = "";
                                var reviewBtnCk = "";
                                    console.log("result.data[4]", result.data[4])
                                let text = ""
                                for (let i = 0; i < result.data.length; i++) {
                                    if(result.data[i].preview != null){
                                        text = result.data[i].preview.replaceAll('<br>','\r\n')
                                    }
                                    if(result.data[i].pcounselingCk == 'T' && (result.data[i].preview == null || result.data[i].pgrade == null)){
                                        reviewBtnCk =
                                            `<td>
                                                <button aria-controls="collapseExample" aria-expanded="false" 
                                                    class="btn btn-sm btn-secondary counselBtn review_write"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapseExample${result.data[i].pcounselingIdx}"
                                                    id="reviewBtn${result.data[i].pcounselingIdx}" type="button">후기 작성
                                                </button>
                                            </td>`
                                    }else if(result.data[i].pcounselingCk == 'T' && result.data[i].preview != null && result.data[i].pgrade != null){
                                        reviewBtnCk =
                                            `<td>
                                                <button aria-controls="collapseExample" aria-expanded="false"
                                                    class="btn btn-sm btn-secondary counselBtn review_open"
                                                    data-bs-toggle="collapse"
                                                    data-bs-target="#collapseExample${result.data[i].pcounselingIdx}"
                                                    id="reviewBtn${result.data[i].pcounselingIdx}" type="button">후기 보기
                                                </button>
                                            </td>`
                                    }else if(result.data[i].pcounselingCk != 'T'){
                                        reviewBtnCk =
                                            `<td>
                                                <button aria-controls="collapseExample" aria-expanded="false"
                                                    class="btn btn-sm btn-outline-secondary counselBtn confirm_wait"
                                                    data-bs-toggle="collapse" disabled
                                                    data-bs-target="#collapseExample${result.data[i].pcounselingIdx}"
                                                    id="reviewBtn${result.data[i].pcounselingIdx}" type="button">확인 대기
                                                </button>
                                            </td>`
                                    }

                                    reviewHtml +=
                                    `<!-- 상담 카드 -->
                                    <div class="outer  mt-2 mx-3">
                                        <div class="plannerCard">
                                            <div class="Photosec mx-2 my-2">
                                                <div>
                                                    <img class="plannerPhoto mt-2" src="/imgs/PlannerPhoto.jpg">
                                                </div>
                                            </div>
                                            <div class="plannerInfo mx-2">
                                                <table class="table table-borderless my-2">
                                                    <tbody>
                                                        <tr hidden>
                                                            <th scope="row">인덱스번호</th>
                                                            <td id="u_idx${result.data[i].pcounselingIdx}">${result.data[i].uidx}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">플래너사진이름</th>
                                                            <td id="photoName${result.data[i].pcounselingIdx}">${result.data[i].pimgName}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">플래너사진경로</th>
                                                            <td id="photoPath${result.data[i].pcounselingIdx}">${result.data[i].pimgPath}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">플래너사진타입</th>
                                                            <td id="photoType${result.data[i].pcounselingIdx}">${result.data[i].pimgType}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">상담사명</th>
                                                            <td>${result.data[i].uname}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">상담일자</th>
                                                            <td>${result.data[i].counselDate}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">견적</th>
                                                            <td class="price">${result.data[i].pprice}원</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row">연락처</th>
                                                            <td>${result.data[i].utel}</td>
                                                        </tr>
                                                        <tr>
                                                            <th scope="row"></th>
                                                            ${reviewBtnCk}
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="commentSection mx-3">
                                            <form class="collapse" id="collapseExample${result.data[i].pcounselingIdx}">
                                                <!--별점기능-->
                                                <div class="outerStar">
                                                    <span class="star text">★★★★★
                                                        <span class="star${result.data[i].pcounselingIdx}"
                                                        style="width:${result.data[i].pgrade * 10}%">★★★★★</span>
                                                        <input class="star" max="10" min="0" step="1" id="star${result.data[i].pcounselingIdx}"
                                                            value="${result.data[i].pgrade}" type="range">
                                                    </span>
                                                </div>
                                                <!--여기까지-->
                                                <table class="table border">
                                                    <tr>
                                                        <th style="vertical-align: middle;">TextArea<br/>
                                                            <sup>(<span id="nowByte${result.data[i].pcounselingIdx}">0</span>/1000bytes)</sup>
                                                        </th>
                                                        <td>
                                                            <form id="frm_textArea" name="frm_textArea">
                                                                <textarea class="form-control commentTextarea"
                                                                    name="textArea_byteLimit" rows="10"
                                                                    id="textArea_byteLimit${result.data[i].pcounselingIdx}">${text}</textarea>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </form>
                                        </div>
                                    </div>`
                                }
                                $('.myCounsel').append(reviewHtml)
                                if (result.data.length < 10) {
                                    endCk = true;
                                }
                            })
                        }
                    }

                    // ck로 각 무한스크롤 페이지 판단
                    if (ck == "mr") {
                        let sessionUname = document.querySelector('.sessionUname').textContent;

                        let newSortData = {
                            offset: sortCnt,
                        }
                        if(endCk != true) {
                        axios.post("/myReserveData", newSortData)
                            .then((result) => {
                                console.log("result", result)
                                let reviewHtml = "";
                                console.log("result.data[1]", result.data[1])
                                for (let i = 0; i < result.data.length; i++) {
                                    reviewHtml +=
                                        `<div id="cancleNum${result.data[i].hreserveIdx}">
                                    <!--예약 확인 카드-->
                                    <div class="reserveCard mx-2 my-2">
                                        <div class="Photosec mx-2 my-2">
                                            <div>
                                                <img class="wedPhoto" src="/imgs/ReserveWed.jpg">
                                            </div>
                                        </div>
                                        <div class="reserveInfo mx-2">
                                            <table class="table table-borderless my-2">
                                                <tbody>
                                                <tr hidden>
                                                    <th scope="row">인덱스번호</th>
                                                    <td id="u_idx${result.data[i].hreserveidx}">${result.data[i].uidx}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">업체사진이름</th>
                                                    <td id="u_idx${result.data[i].hreserveidx}">${result.data[i].cimgName}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">업체사진경로</th>
                                                    <td id="u_idx${result.data[i].hreserveidx}">${result.data[i].cimgPath}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">업체사진타입</th>
                                                    <td id="u_idx${result.data[i].hreserveidx}">${result.data[i].cimgType}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">웨딩홀명</th>
                                                    <td>${result.data[i].uname}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">예약홀명</th>
                                                    <td>${result.data[i].hname}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">예약일자</th>
                                                    <td>${result.data[i].reserveDate}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">예약시간</th>
                                                    <td>${result.data[i].stime}-${result.data[i].etime}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">연락처</th>
                                                    <td>${result.data[i].utel}</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row">금액</th>
                                                    <td class="price">${result.data[i].hprice}원</td>
                                                </tr>
                                                <tr>
                                                    <th scope="row"></th>
                                                    <td>
                                                        <button type="button" class="btn btn-sm btn-light border-dark reserve_modal" data-bs-toggle="modal"
                                                                data-bs-target="#cancelModal1${result.data[i].hreserveIdx}"
                                                                id="reserve_cancelBtn${result.data[i].hreserveIdx}">예약 취소</button>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="modal fade" id="cancelModal1${result.data[i].hreserveIdx}"
                                        data-bs-backdrop="static" data-bs-keyboard="false"  tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" style="height: 40vh">
                                            <div class="modal-content">
                                                <div class="modal-body text-center">
                                                    <button type="button" class="btn-close float-end" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    <strong>
                                                        <p>${sessionUname}님,</p>
                                                        예약 취소하시겠습니까?
                                                    </strong>
                                                    <div class="d-grid gap-2 col-4 mx-auto mt-4">
                                                        <button type="button" class="btn btn-light btn-outline-dark" data-bs-dismiss="modal" data-bs-toggle="modal"
                                                                data-bs-target="#cancelModal2${result.data[i].hreserveIdx}"
                                                                id="cancelBtn${result.data[i].hreserveIdx}">확인</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- 삭제 결과 모달 -->
                                    <div class="modal fade" id="cancelModal2${result.data[i].hreserveIdx}"
                                        data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                        <div class="modal-dialog modal-dialog-centered" style="height: 40vh">
                                            <div class="modal-content">
                                                <div class="modal-body text-center">
                                                    <strong>
                                                        삭제되었습니다.
                                                    </strong>
                                                    <div class="d-grid gap-2 col-4 mx-auto mt-4">
                                                        <button type="button" class="btn btn-light btn-outline-dark" data-bs-dismiss="modal"
                                                        id="resultBtn${result.data[i].hreserveIdx}">확인</button>
                                                    </div>
                                                </div>
                                            </div>
                                            </div>
                                        </div>
                                    </div>`
                                }
                                $('.myReserve').append(reviewHtml)
                                if (result.data.length < 10) {
                                    endCk = true;
                                }
                            })
                        }
                    }



                // ck로 각 무한스크롤 페이지 판단
                    if (ck == "pc") {
                        let newSortData = {
                            offset: sortCnt,
                        }
                        if(endCk != true) {
                            axios.post("/plannerCheckCounselDada", newSortData)
                                .then((result) => {
                                    console.log("result", result)
                                    let reviewHtml = "";
                                    var counselBtnCk = "";
                                    console.log("result.data[1]", result.data[1])

                                    for (let i = 0; i < result.data.length; i++) {
                                        if(result.data[i].pcounselingCk == 'F'){
                                            counselBtnCk =
                                                `<button type = "button"
                                                    class = "btn btn-sm btn-secondary counselCheckBtn"
                                                    data-bs-toggle = "modal"
                                                    id = "counselCheck${result.data[i].pcounselingIdx}"
                                                    data-bs-target = "#checkCounsel${result.data[i].pcounselingIdx}">상담대기</button>`
                                        }else if(result.data[i].pcounselingCk == 'T'){
                                            counselBtnCk =
                                                `<button type = "button"
                                                    class = "btn btn-sm btn-outline-secondary counselCheckBtn"
                                                    data-bs-toggle = "modal"
                                                    id = "counselCheck${result.data[i].pcounselingIdx}"
                                                    data-bs-target = "#checkCounsel${result.data[i].pcounselingIdx}" disabled>상담완료</button>`
                                        }
                                        reviewHtml +=
                                            `
                                            <!-- 컨텐츠 -->
                                        <div class="content">
                                            <div class="flexDiv mx-3 mt-3">
                                                <div class="hallCardSec mt-1 my-1">
                                                    <div class="hallCard">
                                                        <div class="wedInfo mx-3">
                                                            <table class="table table-borderless my-2">
                                                                <tbody>
                                                                <tr>
                                                                    <th scope="row">상담자명</th>
                                                                    <td>${result.data[i].uname}</td>
                                                                </tr>
                                                                <tr>
                                                                    <th scope="row">연락처</th>
                                                                    <td>${result.data[i].utel}</td>
                                                                </tr>
                                                                </tbody>
                                                            </table>
                                                        </div>
                                                            ${counselBtnCk}
                                                        </div>
                                                </div>
                            
                                            </div>
                                            <div class="modal fade" id="checkCounsel${result.data[i].pcounselingIdx}" data-bs-backdrop="static" data-bs-keyboard="false"
                                                 tabindex="-1"
                                                 aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                                <div class="modal-dialog modal-dialog-centered" style="height: 40vh">
                                                    <div class="modal-content">
                                                        <div class="modal-body text-center">
                                                            <button type="button" class="btn-close float-end" data-bs-dismiss="modal"
                                                                    aria-label="Close"></button>
                                                            <strong>
                                                                상담을 완료하시겠습니까?
                                                            </strong>
                                                        </div>
                                                        <div class="d-grid gap-2 col-4 mx-auto mt-4 mb-3">
                                                            <button type="button" class="btn btn-light btn-outline-dark checkCounselBtn" data-bs-dismiss="modal"
                                                                    id="checkCounselBtn${result.data[i].pcounselingIdx}">
                                                                확인
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                            `
                                    }
                                    $('.plannerCounsel').append(reviewHtml)
                                    if (result.data.length < 10) {
                                        endCk = true;
                                    }
                                })
                        }
                    }



                    if (ck == "pd") {
                        let newSortData = {
                            offset: sortCnt,
                            columnData: columnData,
                            sortData: sortData,
                        }
                        if(endCk != true) {
                            axios.post("", newSortData)
                                .then((result) => {
                                    console.log("result", result)
                                    let reviewHtml = "";
                                    console.log("result.data[1]", result.data[1])
                                    for (let i = 0; i < result.data.length; i++) {
                                        reviewHtml +=
                                            `<`
                                    }
                                    $('.').append(reviewHtml)
                                    if (result.data.length < 10) {
                                        endCk = true;
                                    }
                                })
                        }
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
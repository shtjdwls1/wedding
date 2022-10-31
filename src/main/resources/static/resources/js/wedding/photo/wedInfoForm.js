"use strict";


import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new wedInfoForm();
})

export class wedInfoForm {
    constructor() {
        console.log("wedInfoForm")
        this.eventBinding();
    }
    eventBinding() {
        // 정보등록 버튼 클릭시 이벤트
        //TODO 등록완료 버튼 클릭시 DB UPDATE 실행 후 "수정완료되었습니다" 모달창, 확인버튼 클릭시 메인으로 이동
        $('#wedInfoSubmit').on('click', name => {
            let wedInfoFormData = new FormData($('#wedInfoForm')[0]);
            console.log(wedInfoFormData.get("files").size)
            if(wedInfoFormData.get("files").size!==0){
                let file = $('#files')[0].files;
                wedInfoFormData.append("files",file[0])
            }
            axios.post("/data/photo/upload",wedInfoFormData).then((result)=>{
                console.log(result.data);
                location.href = "/chkWedInfo"
            })
        })
        //TODO 수정완료 버튼 클릭시 DB UPDATE 실행 후 "수정완료되었습니다" 모달창, 확인버튼 클릭시 메인으로 이동
        $('#wedInfoUpdate').on('click', name => {
            let wedInfoFormData = new FormData($('#wedInfoForm')[0]);
            if(wedInfoFormData.get("files").size!==0){
                let file = $('#files')[0].files;
                // wedInfoFormData.append("files",file[0])
                for (let key of wedInfoFormData.keys()) {
                    console.log(key, ":", wedInfoFormData.get(key));
                }
            }
            modalOn();
            $('#wedInfoModalClose').on('click',()=>{
                modalClose();
                axios.post("/data/photo/update",wedInfoFormData).then((result)=>{
                    console.log(result.data);
                    location.href = "/chkWedInfo"
                })
            })
        })
        // 파일 선택시 미리보기
        $("#files").on('change', () => {
            let img = $('#files')[0].files;
            let reader = new FileReader();
            reader.readAsDataURL(img[0]);
            reader.onload = function (e) {
                $('#thumbImg').attr('src', e.target.result).attr('object-fit', "contain");
            }
        });

        // 모달창 닫기
        function modalClose(){
            $('body').removeClass('modal-open')
                .css('overflow','').css('padding-right','')
            $('#wedInfoModal').removeClass('show')
                .css('display','none')
                .attr('aria-hidden','true')
                .removeAttr('aria-modal').removeAttr('role')
            $('.modal-backdrop').remove()
        }
        // 모달창 열기
        function modalOn(){
            $('body').addClass('modal-open')
                .css('overflow','hidden').css('padding-right','0px')
                .append("<div class='modal-backdrop fade show'></div>")
            $('#wedInfoModal').addClass('show')
                .css('display','block')
                .removeAttr('aria-hidden')
                .attr('aria-modal','true').attr('role','dialog')
        }
        // 사업자 등록번호 자릿수 제한
        $('#business1').on('keyup',(e)=>{
            let len = $(this).val()
            if(len.length>3){
                $(this).val($(this).val().substring(0,3));
            }
        })
        // 다음주소api 기능
        $('#address').on('click',()=>{
            new daum.Postcode({
                oncomplete: function(data) {
                    // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                    // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                    // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                    var addr = ''; // 주소 변수
                    var extraAddr = ''; // 참고항목 변수

                    //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                    if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                        addr = data.roadAddress;
                    } else { // 사용자가 지번 주소를 선택했을 경우(J)
                        addr = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if(data.userSelectedType === 'R'){
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                            extraAddr += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if(data.buildingName !== '' && data.apartment === 'Y'){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }
                        // 조합된 참고항목을 해당 필드에 넣는다.
                        document.getElementById("detailAddr").value = extraAddr;

                    } else {
                        document.getElementById("detailAddr").value = '';
                    }

                    // 우편번호와 주소 정보를 해당 필드에 넣는다.
                    document.getElementById("address").value = addr;
                    // 커서를 상세주소 필드로 이동한다.
                    document.getElementById("detailAddr").focus();
                }
            }).open();
        })
    }
}
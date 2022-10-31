"use strict";


import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new wedInfo();
})

export class wedInfo {
    constructor() {
        console.log("wedInfo")
        this.eventBinding();
    }
    eventBinding() {
        // 업체정보 수정페이지로 이동
        $('.changeWedBtn').on('click',()=>{
            location.href = "/wedInfoForm"
        })

        // 홀 수정하기 버튼 클릭시 페이지 열기닫기
        $('.changeHallBtn').on('click',()=>{
            if($('.updateHallForm').hasClass('hidden')){
                $('.hallCard').css('border-bottom-right-radius','0vh')
                    .css('border-bottom-left-radius','0vh')
                    .css('border-bottom', '0px solid black')
                $('.updateHallForm').removeClass('hidden')
                    .css('border-top-right-radius','0vw')
                    .css('border-top-left-radius','0vw')
            }else{
                $('.hallCard').css('border-bottom-right-radius','2vh')
                    .css('border-bottom-left-radius','2vh')
                    .css('border-bottom', '1px solid black')
                $('.updateHallForm').addClass('hidden')
                    .css('border-top-right-radius','2vw')
                    .css('border-top-left-radius','2vw')
            }
        })
        $('#addHallBtn').on('click',()=>{
            if($('.addHallForm').hasClass('hidden')){
                $('.addHallForm').removeClass('hidden')
                    .css('border-top-right-radius','0vw')
                    .css('border-top-left-radius','0vw')
            }else{
                $('.addHallForm').addClass('hidden')
                    .css('border-top-right-radius','2vw')
                    .css('border-top-left-radius','2vw')
            }
        })
        let fileNo = 0;
        let filesArr = [];
        let curFileCnt
        $('#files').on('change',(e)=>{
            addFile(e.target)

        });
        // 미리보기 이미지 클릭이벤트
        // 리스트에서 삭제됨
        $(document).on('click',".delete",(e)=>{
            let id = $(e.target).closest('div').attr('id') // 인덱스를 갖고있는 id가져오기
            let idlen = id.length // 길이확인 2자리일수도 있으니까
            let idx = id.substring(4,idlen+1) //filexx 뒤에서부터 자르기
            deleteFile(idx)

        })
        // 업로드 할 이미지 추가
        function addFile(obj){
            let maxFileCnt = 12;   // 첨부파일 최대 개수
            let attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
            let remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
            curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

            // 첨부파일 개수 확인
            if (curFileCnt > remainFileCnt) {
                alert("첨부파일은 최대 " + maxFileCnt + "개 까지 첨부 가능합니다.");
            }

            for (let i = 0; i < Math.min(curFileCnt, remainFileCnt); i++) {

                const file = obj.files[i];

                // 첨부파일 검증
                if (validation(file)) {
                    // 파일 배열에 담기
                    let reader = new FileReader();
                    reader.onload = function (e) {
                        filesArr.push(file);

                        let htmlData = '';
                        htmlData = `<div id="file${fileNo}" class="filebox">
                                    <a class="delete">x<img src="${e.target.result}" data-file="${file.name}" class="selProductFile" title="Click to remove" ><i class="fa fa-minus-square"></i></a>
                                </div>`
                        $('.file-list').append(htmlData);
                        fileNo++;

                    };
                    reader.readAsDataURL(file)
                    // 목록 추가

                } else {
                    continue;
                }
            }
            // 초기화
            document.querySelector("input[type=file]").value = "";
        }
        // 이미지 유효성 검증
        function validation(obj){
            const fileTypes = ['image/gif', 'image/jpeg', 'image/png'];
            if (obj.name.length > 100) {
                alert("파일명이 100자 이상인 파일은 제외되었습니다.");
                return false;
            } else if (obj.size > (100 * 1024 * 1024)) {
                alert("최대 파일 용량인 100MB를 초과한 파일은 제외되었습니다.");
                return false;
            } else if (obj.name.lastIndexOf('.') === -1) {
                alert("확장자가 없는 파일은 제외되었습니다.");
                return false;
            } else if (!fileTypes.includes(obj.type)) {
                alert("첨부가 불가능한 파일은 제외되었습니다.");
                return false;
            } else {
                return true;
            }
        }
        /* 첨부파일 삭제 */
        function deleteFile(num) {
            document.querySelector("#file" + num).remove();
            filesArr[num].is_delete = true;
        }
        // 홀 정보등록버튼 클릭이벤트
        $('#addHallData').on('click',()=>{
            let addHallDataFormData = new FormData($('#addHallDataForm')[0]);
            if(filesArr.length!==0){ // 파일이 존재한다면
                console.log(filesArr.length)
                for (let i = 0; i< filesArr.length; i++){
                    // 삭제되지 않은 파일만 폼데이터에 담기
                    if (!filesArr[i].is_delete) {
                        addHallDataFormData.append("files", filesArr[i])
                        console.log('expected append : ')
                        console.log(filesArr[i])
                    }
                }
                console.log('append result : ')
                for (let value of addHallDataFormData.values()){
                    console.log(value);
                }
            }
            modalOn();
            $('#hallInfoModalClose').on('click',()=>{
                modalClose();
                axios.post("/data/hall/upload",addHallDataFormData).then((result)=>{
                    console.log(result.data);
                    // location.href = "/chkWedInfo"
                })
            })

        })
        // 모달창 닫기
        function modalClose(){
            $('body').removeClass('modal-open')
                .css('overflow','').css('padding-right','')
            $('#hallInfoModal').removeClass('show')
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
            $('#hallInfoModal').addClass('show')
                .css('display','block')
                .removeAttr('aria-hidden')
                .attr('aria-modal','true').attr('role','dialog')
        }
    }

}
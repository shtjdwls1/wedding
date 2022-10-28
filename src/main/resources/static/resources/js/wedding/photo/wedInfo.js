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
        $('#addHallData').on('click',()=>{
            let addHallDataFormData = new FormData($('#addHallDataForm')[0]);
            console.log(addHallDataFormData.get("file").size)
            if(addHallDataFormData.get("file").size!==0){
                let file = $('#files')[0].files;
                addHallDataFormData.append("files",file[0])
            }
            axios.post("/data/photo/upload",addHallDataFormData).then((result)=>{
                console.log(result.data);
                location.href = "/chkWedInfo"
            })
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
        $('#files').on('change',(e)=>{
            addFile(e.target)
        });
        $(document).on('click',".delete",(e)=>{
            let id = $(e.target).closest('div').attr('id') // 인덱스를 갖고있는 id가져오기
            let idlen = id.length // 길이확인 2자리일수도 있으니까
            let idx = id.substring(4,idlen+1) //filexx 뒤에서부터 자르기
            deleteFile(idx)

        })

        function addFile(obj){
            let maxFileCnt = 12;   // 첨부파일 최대 개수
            let attFileCnt = document.querySelectorAll('.filebox').length;    // 기존 추가된 첨부파일 개수
            let remainFileCnt = maxFileCnt - attFileCnt;    // 추가로 첨부가능한 개수
            let curFileCnt = obj.files.length;  // 현재 선택된 첨부파일 개수

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
                                    <a class="delete"><img src="${e.target.result}" data-file="${file.name}" class="selProductFile" title="Click to remove" ><i class="far fa-minus-square"></i></a>
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

    }

}
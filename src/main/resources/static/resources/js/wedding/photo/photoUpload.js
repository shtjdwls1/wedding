"use strict";


import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new photoUpload();
})

export class photoUpload{
    constructor() {
        console.log("photoUpload")
        this.eventBinding();
    }
    eventBinding() {
        // 정보등록 버튼 클릭시 이벤트
        $('#wedInfoSubmit').on('click', name => {
            let wedInfoFormData = new FormData($('#wedInfoForm')[0]);
            let file = $('#files')[0].files;
            wedInfoFormData.append("files",file[0])
            axios.post("/data/photo/upload",wedInfoFormData).then((result)=>{
                console.log(result.data);
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
    }

}
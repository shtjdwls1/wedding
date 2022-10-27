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
        $('#files').on('change',handleImgFileSelect);





    }

}
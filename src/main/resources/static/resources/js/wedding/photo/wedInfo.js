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

    }

}
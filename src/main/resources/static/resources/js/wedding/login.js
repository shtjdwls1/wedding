"use strict";

import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new Login();
})

export class Login{
    constructor() {
        this.eventBinding();
    }

    eventBinding(){
        $('#login_btn').on('click',()=>{

            // TODO 전부다 입력했을때
            // TODO 입력한 아이디 비밀번호 DB조회, 일치하면 세션에 저장후 메인으로 이동 틀리면 모달창 띄우기
            let id = $('#inputId').val();
            let pw = $('#inputPassword').val();
            let data ={
                "uid" : id,
                "upw" : pw };
            console.log(data)
            if (id !== '' && pw !==''){
                axios.post('/data/login',data).then((result)=>{
                    console.log(result)
                    if(result.data===1) {
                        location.href = "/";
                    }else{
                        modalOn()
                    }
                })
            }else{
                modalOn()
            }
        })
        $('#loginModalClose').on('click',()=>{
            modalClose()
        })
        function modalOn(){
            $('body').addClass('modal-open')
                .css('overflow','hidden').css('padding-right','0px')
                .append("<div class='modal-backdrop fade show'></div>")
            $('#loginModal').addClass('show')
                .css('display','block')
                .removeAttr('aria-hidden')
                .attr('aria-modal','true').attr('role','dialog')
        }
        function modalClose(){
            $('body').removeClass('modal-open')
                .css('overflow','').css('padding-right','')
            $('#loginModal').addClass('show')
                .css('display','none')
                .attr('aria-hidden','true')
                .removeAttr('aria-modal').removeAttr('role')
            $('.modal-backdrop').remove()
        }
        $('.modal-dialog-centered').on('click',()=>{
            modalClose()
        })
        // 비밀번호 표시
        $('.pw_hide').on('click',(e) => {
            if ($('.pw_hide').hasClass('active')){
                $('.pw_hide').removeClass('active');
                $('#inputPassword').prop("type", "password");
            }else{
                $('.pw_hide').addClass('active');
                $('#inputPassword').prop("type", "text");
            }
        })


    }

}
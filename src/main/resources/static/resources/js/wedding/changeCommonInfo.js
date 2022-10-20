"use strict";

import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new ChangeCommonInfo();
})

export class ChangeCommonInfo
{
    constructor() {
        console.log('changeCommonInfo')
        this.eventBinding();
    }
    eventBinding(){
        // 비밀번호 입력후 정보수정 버튼 클릭시 이벤트
        $('#beforeUpdate').on('click',()=>{
            let pw = $('#exampleInputPassword1').val()
            axios.post('/data/updateChkPw',{"upw":pw}).then((result)=>{
                console.log(result.data)
                if(result.data===1){
                    location.href="/changeMyInfoForm"
                }else{
                    $('#pwsc').css("color","red")
                }
            })
        })
        // 비밀번호창 포커싱시 이벤트
        $('#exampleInputPassword1').on("focus",(e)=>{
            $(e.currentTarget).val('')
            $('#pwsc').css("color",'white')
        })
        // 회원탈퇴버튼 클릭시 이벤트
        $('#btnDeleteInfo').on('click',()=>{
            modalOn()
            $('#deleteModalClose').on('click',()=>{
                modalClose()
                axios.post('/data/deleteInfo').then((result)=>{

                })
            });
        })
        function modalOn(){
            $('body').addClass('modal-open')
                .css('overflow','hidden').css('padding-right','0px')
                .append("<div class='modal-backdrop fade show'></div>")
            $('#deleteModal').addClass('show')
                .css('display','block')
                .removeAttr('aria-hidden')
                .attr('aria-modal','true').attr('role','dialog')
        }
        function modalClose(){
            $('body').removeClass('modal-open')
                .css('overflow','').css('padding-right','')
            $('#deleteModal').addClass('show')
                .css('display','none')
                .attr('aria-hidden','true')
                .removeAttr('aria-modal').removeAttr('role')
            $('.modal-backdrop').remove()
        }
    }
}
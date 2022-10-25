"use strict";

import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new ChangeCommonInfoForm();
})

export class ChangeCommonInfoForm
{
    constructor() {
        console.log('changeCommonInfoForm')
        this.eventBinding();
    }
    eventBinding(){
    //TODO 비밀번호 동일성체크
        $('#checkPw').on('keyup',(e)=>{
            if($('#checkPw').val()!==''){
                if($('#inputPw').val()===$('#checkPw').val()){
                    $('#pwsc').html("").removeAttr("color")
                }else{
                    $('#pwsc').html("입력한 비밀번호와 다릅니다.").css("color","red")
                }
            }else{
                $('#pwsc').html("비밀번호를 입력해주세요.").css("color","red")
            }
        })
    //TODO 공백여부 체크
        function checkAll() {
            let cnt = 0;
            if ($('#inputPw').val() === "") {
                $('#ipwc').html("비밀번호를 입력해주세요.").css("color","red");
                cnt++;
            }
            if ($('#checkPw').val() === "") {
                cnt++;
            }
            if ($('#inputPw').val() !== $('#checkPw').val()) {
                cnt++;
            }

            if ($('#inputName').val() === "") {
                $('#inc').html("이름을 입력해주세요.").css("color","red")
                cnt++;
            }
            if ($('#tel1').val() === "") {
                $('#t123c').html("연락처를 입력해주세요.").css("color","red")
                cnt++;
            }
            if ($('#tel2').val() === "") {
                $('#t123c').html("연락처를 입력해주세요.").css("color","red")
                cnt++;
            }
            if ($('#tel3').val() === "") {
                $('#t123c').html("연락처를 입력해주세요.").css("color","red")
                cnt++;
            }
            return cnt;
        }
    //TODO 공백칸 포커스 됐을때 경고문구 지우기
    $('#inputPw').on('keyup',()=>{
        $('#ipwc').html("").removeAttr("color")
    })
    $('#inputName').on('keyup',()=>{
        $('#inc').html("").removeAttr("color")
    })
    $('#tel1').on('keyup',()=>{
        $('#t123c').html("").removeAttr("color")
    })
    $('#tel2').on('keyup',()=>{
        $('#t123c').html("").removeAttr("color")
    })
    $('#tel3').on('keyup',()=>{
        $('#t123c').html("").removeAttr("color")
    })
    //TODO 수정완료 버튼 클릭시 DB UPDATE 실행 후 "수정완료되었습니다" 모달창, 확인버튼 클릭시 메인으로 이동
    $('#update').on('click',()=>{
        if(checkAll()===0){
            console.log("입력성공")
            let UpdateInfoFormData = SerializeObject.run('UpdateInfoForm');
            console.log("UIFD==>{}",UpdateInfoFormData)
            axios.post("/data/update",UpdateInfoFormData).then((result)=>{
                console.log(result.data)
                if(result.data===1){
                    console.log("수정성공")
                    location.replace("/")
                }else{
                    console.log("수정실패")
                }
            })
        }else{
            console.log("입력실패")
        }

    })
    }
}
// 회원가입
"use strict";

import {SerializeObject} from "@/module/common/serializeObject";

$(()=>{
    new Join();
})

export class Join
{
    constructor() {
        console.log('join')
        this.eventBindgin();
    }
    eventBindgin(){
        const clasifyBtn = document.getElementById('clasifyBtn');
        const clasifyModal = document.querySelector('.clasifyModal');
        const clasifyInput = document.querySelector("#clasify")
        const locationInput = document.querySelector('#checkCity')

// 모달 창 나타나기, 엑스버튼 클릭시 모달창 사라지기.
        function controllClasifyModal() {
            clasifyModal.classList.toggle('hidden')
        }

//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === clasifyModal ? clasifyModal.classList.add('hidden') : false
        })


// 옵션 클릭시 색 변경, 체크 추가
        const modalOption = document.getElementsByClassName("modalOption");
        const clasifyOption = document.getElementsByClassName("clasifyOption");
        const locationOption = document.getElementsByClassName("locationOption")

        function handleClickClasify(event) {
            console.log(event.target);
            // console.log(this);
            // 콘솔창을 보면 둘다 동일한 값이 나온다

            console.log("event.target.classList", event.target.classList);

            if (event.target.classList[1] === "clicked") {
                event.target.classList.remove("clicked");

            } else {
                for (var i = 0; i < clasifyOption.length; i++) {
                    clasifyOption[i].classList.remove("clicked");
                }

                event.target.classList.add("clicked");
                console.log("누른거!" + event.target.textContent)

                clasifyInput.innerText = event.target.textContent;
                controllClasifyModal();

            }
        }


        function handleClickLocation(event) {
            console.log(event.target);
            // console.log(this);
            // 콘솔창을 보면 둘다 동일한 값이 나온다

            console.log("event.target.classList", event.target.classList);

            if (event.target.classList[1] === "clicked") {
                event.target.classList.remove("clicked");

            } else {
                for (var i = 0; i < locationOption.length; i++) {
                    locationOption[i].classList.remove("clicked");
                }

                event.target.classList.add("clicked");
                console.log("누른거!" + event.target.textContent)

                locationInput.value = event.target.textContent;
                controllLocationModal();

            }
        }


        function init() {
            for (var i = 0; i < clasifyOption.length; i++) {
                clasifyOption[i].addEventListener("click", handleClickClasify);
            }
            for (var i = 0; i < locationOption.length; i++) {
                locationOption[i].addEventListener("click", handleClickLocation);
            }
        }

        init();

//지역 모달
        const locationModal = document.querySelector('.locationModal')

// 지역 선택 모달 창 나타나기, 사라지기
        function controllLocationModal() {
            locationModal.classList.toggle('hidden')
        }

//외부 클릭시 모달창 닫기
        window.addEventListener('click', (e) => {
            e.target === locationModal ? locationModal.classList.add('hidden') : false
        })

        $('.btn_wedding_join').on('click',(e)=>{
            let joinFormData = SerializeObject.run('wedding_joinForm');
            console.log("wjfd==>{}",joinFormData)
            console.log(joinFormData)
            if(checkAll()){
                axios.post('/data/join',joinFormData).then((result)=> {
                    if(result.data>0){
                        alert("가입성공")
                        location.href = "index";
                    }else{
                        alert("정보를 올바르게 입력해주세요")
                        location.href="join";
                    }
                })
            }
        })
        /*
         * 이름 입력시 한글만 입력되도록
         */
        $('#inputName').on('keyup',()=>{
            const regexp = /[a-z0-9]|[ \[\]{}()<>?|`~!@#$%^&*-_+=,.;:"'\\]/g;
            let v = $('#inputName').val();
            if (regexp.test(v)) {
                $('#inputName').val("");
            }
        })
        /*
         * 연락처 입력시 숫자만 입력되도록
         */
        $('#tel1').on('keyup',()=>{
            const regexp = /[^0-9]/gi;
            let v = $('#tel1').val();
            if (regexp.test(v)) {
                $('#tel1').val("");
            }
        })
        $('#tel2').on('keyup',()=>{
            const regexp = /[^0-9]/gi;
            let v = $('#tel2').val();
            if (regexp.test(v)) {
                $('#tel2').val("");
            }
        })
        $('#tel3').on('keyup',()=>{
            const regexp = /[^0-9]/gi;
            let v = $('#tel3').val();
            if (regexp.test(v)) {
                $('#tel3').val("");
            }
        })
        /*
        * 비밀번호 유효성 체크
        */
        $('#inputPw').on('keyup',(e)=>{
            //비밀번호 대소문자,길이 체크
            let pass = $(e.currentTarget).val();
            let message = "";
            let color = "";
            let checkPoint = 0;

            // 입력값이 있을경우에만 실행
            if(pass.length) {

                // 최대 입력 글자수를 제한한다.
                if(pass.length===0){
                    $('#pwc').addClass("hidden")
                }else if(pass.length < 8 || pass.length > 16) {
                    message = "최소 8자 이상, 최대 16자 이하";
                    color = "#A23E48";
                }

                // 문자열의 길이가 8 ~ 16 인경우
                else {

                    // 비밀번호 문자열에 숫자 존재 여부 검사
                    let pattern1 = /[0-9]/;  // 숫자
                    if(pattern1.test(pass) === false) {
                        checkPoint = checkPoint + 1;
                    }

                    // 비밀번호 문자열에 영문 소문자 존재 여부 검사
                    let pattern2 = /[a-z]/;
                    if(pattern2.test(pass) === false) {
                        checkPoint = checkPoint + 1;
                    }

                    // 비밀번호 문자열에 영문 대문자 존재 여부 검사
                    let pattern3 = /[A-Z]/;
                    if(pattern3.test(pass) === false) {
                        checkPoint = checkPoint + 1;
                    }

                    // 비밀번호 문자열에 특수문자 존재 여부 검사
                    let pattern4 = /[~!@#$%^&*()_+|<>?:{}]/;  // 특수문자
                    if(pattern4.test(pass) === false) {
                        checkPoint = checkPoint + 1;
                    }

                    if(checkPoint >= 3) {
                        message = "보안성이 취약한 비밀번호";
                        color = "#A23E48";
                    } else if(checkPoint === 2) {
                        message = "보안성이 낮은 비밀번호";
                        color = "#FF8C42";
                    } else if(checkPoint === 1) {
                        message = "보안성이 보통인 비밀번호";
                        color = "#FF8C42";
                    } else {
                        message = "보안성이 강력한 비밀번호";
                        color = "#0000CD";
                    }
                }
            }
            console.log("ku")
            $('#pwc').html(message).css('color',color);
        })
        /*
         * 폼 공백 체크 및 비밀번호 동일 체크
         */
        function checkAll() {
            if ($('#checkId').val() === "") { //해당 입력값이 없을 경우 같은말: if(!uid.value)
                $('#checkId').focus(); //focus(): 커서가 깜빡이는 현상, blur(): 커서가 사라지는 현상
                return false; //return: 반환하다 return false:  아무것도 반환하지 말아라 아래 코드부터 아무것도 진행하지 말것
            }
            if ($('#inputPw').val() === "") {
                $('#inputPw').focus();
                return false;
            }
            if ($('#checkPw').val() === "") {
                $('#checkPw').focus();
                return false;
            }
            if ($('#inputPw').val() !== $('#checkPw').val()) {
                $('#checkPw').focus();
                return false;
            }

            if ($('#inputName').val() === "") {
                $('#inputName').focus();
                return false;
            }
            if ($('#tel1').val() === "") {
                $('#tel1').focus();
                return false;
            }
            if ($('#tel2').val() === "") {
                $('#tel2').focus();
                return false;
            }
            if ($('#tel3').val() === "") {
                $('#tel3').focus();
                return false;
            }
            return true;
        }
    }
}
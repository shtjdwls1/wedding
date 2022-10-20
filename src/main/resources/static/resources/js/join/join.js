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

	locEvent(key){
		console.log(key)
		let locTmpl = require("@/join/want.html")
		let callObj = {'key' : $('#locWantKey').val()};

		axios.post('/data/wantLoc', callObj).then((result)=>{
			console.log(result);
			result.data.title = key.key === 'loc' ? '자기지역' : '배우자 희망지역';
			//console.log(locTmpl(result));
			$('.want_loc').append(locTmpl(result));
			$('.want_loc').removeClass('hidden');


		})
	}

	eventBindgin(){
		// 회원가입 유효성 풀체크
		$('.btn_wedding_join').on('click',(e)=>{
			let joinFormData = SerializeObject.run('wedding_joinForm');
			console.log("wjfd==>{}",joinFormData)
			console.log(joinFormData)
			if(checkAll()){
				if($('#idc').hasClass("cnuid")){
					axios.post('/data/join',joinFormData).then((result)=> {
						if(result.data>0){
							$('#mdtxt').html("가입을 축하드립니다.")
							modalOn();
							$('#loginModalClose').on('click',()=>{
								modalClose(true)
							});
						}else{
							$('#mdtxt').html("정보를 올바르게 입력해주세요.")
							modalOn();
							$('#loginModalClose').on('click',()=>{
								modalClose(false)
							});
						}
					})
				}else{
					$('#mdtxt').html("정보를 올바르게 입력해주세요.")
					modalOn();
					$('#loginModalClose').on('click',()=>{
						modalClose(false)
					});
				}
			}
		})
		/*
		 * 아이디 중복여부 체크
		 */
		$('#checkId').on("focus",(e)=>{
			$(e.currentTarget).val('')
			$('#idc').addClass("hidden").removeClass("cnuid").removeAttr("color")
		})
		$('#checkId').on('blur',(e)=>{
			let data = {"uid":$('#checkId').val()}
			console.log(data)
			if($(e.currentTarget).val()!==''){
				axios.post('/data/checkId',data).then((result)=>{
						console.log(result)
						if(result.data!==1){
							$('#idc').removeClass("hidden").html("이미 사용중인 아이디입니다.").css("color","red")
						}else{
							$('#idc').addClass("cnuid").removeClass("hidden").html("사용 가능한 아이디입니다.").css("color","green")
						}
					}
				)
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
		 * 비밀번호 동일성 체크
		 */
		$('#checkPw').on('keyup',(e)=>{
			if($('#checkPw').val()!==''){
				if($('#inputPw').val()===$('#checkPw').val()){
					$('#pwsc').html("").removeAttr("color")
				}else{
					$('#pwsc').html("입력한 비밀번호와 다릅니다.").css("color","red")
				}
			}else{
				$('#pwsc').html("").removeAttr("color")
			}
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

		/*
		 * 회원가입 실패시 모달
		 */

		function modalClose(tf){
			$('body').removeClass('modal-open')
				.css('overflow','').css('padding-right','')
			$('#loginModal').addClass('show')
				.css('display','none')
				.attr('aria-hidden','true')
				.removeAttr('aria-modal').removeAttr('role')
			if(tf===true){
				location.href="/login"
			}
		}
		function modalOn(){
			$('body').addClass('modal-open')
				.css('overflow','hidden').css('padding-right','0px')
			$('#loginModal').addClass('show')
				.css('display','block')
				.removeAttr('aria-hidden')
				.attr('aria-modal','true').attr('role','dialog')
		}
	}
}
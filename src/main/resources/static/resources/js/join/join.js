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
		$('.btn_wedding_join').on('click',(e)=>{
			let joinFormData = SerializeObject.run('wedding_joinForm');
			console.log("wjfd==>{}",joinFormData)
			console.log(joinFormData)
				axios.post('/data/join',joinFormData).then((result)=> {
					if(result.data>0){
						alert("가입성공")
						location.href = "index";
					}else{
						location.href = "join";
					}
				})
		})
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
					message = ":: 최소 8자 이상, 최대 16자 이하 ::";
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
						message = ":: 보안성이 취약한 비밀번호 ::";
						color = "#A23E48";
					} else if(checkPoint === 2) {
						message = ":: 보안성이 낮은 비밀번호 ::";
						color = "#FF8C42";
					} else if(checkPoint === 1) {
						message = ":: 보안성이 보통인 비밀번호 ::";
						color = "#FF8C42";
					} else {
						message = ":: 보안성이 강력한 비밀번호 ::";
						color = "#0000CD";
					}
				}
			}
			console.log("ku")
			$('#pwc').html(message).css('color',color);
		})

	}
}
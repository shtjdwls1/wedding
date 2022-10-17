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


	}
}
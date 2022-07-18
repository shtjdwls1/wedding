"use strict";

$(()=>{
	new Sample();
})

export class Sample{
	constructor() {
		this.eventBind();
	}

	eventBind(){
		$('.base_info').on('click', (e)=>{
			console.log('aaa');
			location.href='/join';
		})

		$('#data > span').on('click', (e)=>{
			console.log('eeee')
			console.log('eeeeddd')
			console.log(e);
		})
	}

}
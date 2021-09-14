"use strict";

$(()=>{
	new Sample();
})

export class Sample{
	constructor() {
		this.eventBind();
	}

	eventBind(){
		$('.base_info').on('click', ()=>{
			bridge.startWin('/');
		})
	}

}
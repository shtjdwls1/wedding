"use strict";


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
		$('.btn_slct_area').on('click', (e)=>{
			$('.normal_pop_wrap').removeClass('hidden');
		})
	}



}
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
		$('.slct_man').on('click', ()=>{
			bridge.startWin('/');
		});

		$('.btn_slct_area').on('click', (e)=>{
			let locTmpl = require("loc.html")
			let callObj = {'key' : $('#locWantKey').val()};

			axios.all([axios.post('/data/wantLoc', callObj), axios.post('/data/wantLoc', callObj)]).then(
				axios.spread((first, seconds)=>{
					console.log(first, seconds);
				}))

			axios.post('/data/wantLoc', callObj).then((result)=>{
				//console.log(result);
				//console.log(locTmpl(result));
				$('.want_loc').append(locTmpl(result));
				$('.want_loc').removeClass('hidden');

				$('.btn_complete').on('click', (e)=>{
					let selectedKeyArray = new Array();
					$('.hope_list > li').each((idx, obj)=>{
						if($(obj).children('a').hasClass('active')){
							let wantKey = $(obj).children('a').data('key');
							//console.log(wantKey);
							selectedKeyArray.push(wantKey);
						}
					})
					//console.log(selectedKeyArray);
					//console.log(_.join(selectedKeyArray, ','));
					$('#locWantKey').val(_.join(selectedKeyArray, ','));
					$('.want_loc').empty().addClass('hidden');
				})

				$('.btn_reset').on('click', (e)=>{
					$('.hope_list > li').each((idx, obj)=>{
						let $obj = $(obj).children('a');
						if($obj.hasClass('active')){
							$obj.removeClass('active');
						}
					})
				})

				$('.hope_list > li > a').on('click', (e)=>{
					if($(e.currentTarget).hasClass('active')){
						$(e.currentTarget).removeClass('active');
					}else{
						$(e.currentTarget).addClass('active');
					}
				})
			})
		})


	}
}
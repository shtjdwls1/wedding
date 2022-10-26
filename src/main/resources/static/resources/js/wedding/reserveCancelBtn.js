$(() => {
    new reserveCancelBtn();
})

export class reserveCancelBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        $('.reserve_modal').on('click', (e) => {
            madal_idx(e.target)
        })

        function madal_idx(target){
            const madalIdx = target.id.substring(17);
            console.log("인덱스 체크 ",madalIdx)
            $("#cancelModal1"+madalIdx).modal({backdrop: 'static',keyboard:false});
            $("#cancelModal2"+madalIdx).modal({backdrop: 'static',keyboard:false});

            const updateData = {
                hreserveIdx: madalIdx,
            }
            $("#cancelBtn"+madalIdx).on('click',(e)=>{
                axios.post('/myReserve/cancel', updateData)
                    .then((result) => {
                        console.log(result);
                        if(result.data > 0) {
                            console.log("삭제성공");
                            $("#resultBtn" + madalIdx).on('click', (e) => {
                                location.href = "/myReserve"
                            })
                        }else{
                            console.log("삭제실패");
                        }

                    })
            })


        }
    }
}



$(() => {
    new reserveCancelBtn();
})

export class reserveCancelBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        const madal_idx = (target) => {
            const madalIdx = target.id.substring(17);
            console.log(madalIdx)
            $("#cancelModal1"+madalIdx).modal({backdrop: 'static',keyboard:false});
            $("#cancelModal2"+madalIdx).modal({backdrop: 'static',keyboard:false});

            const targetData = madalIdx;
            console.log("인덱스 확인 ", targetData)
            const updateData = {
                hreserveIdx: targetData,
            }
            $("#cancelBtn"+madalIdx).on('click',(e)=>{
                axios.post('/myReserve/cancel', updateData)
                    .then((result) => {
                        console.log(result);
                        $("#resultBtn"+madalIdx).on('click',(e)=>{
                            location.href="/myReserve"
                        })
                    })
            })


        }
    }
}



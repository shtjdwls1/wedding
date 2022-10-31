$(() => {
    new reserveCancelBtn();
})

export class reserveCancelBtn {
    constructor() {
        this.eventBindgin()
    }

    eventBindgin() {

        $(document).on('click','.reserve_modal', (e) => {
            madal_idx(e.target)
        })

        function madal_idx(target){
            const madalIdx = target.id.substring(17);
            console.log("인덱스 체크 ",madalIdx)

            const updateData = {
                hreserveIdx: madalIdx,
            }
            $(document).on('click',"#cancelBtn"+madalIdx,(e)=>{
                axios.post('/myReserve/cancel', updateData)
                    .then((result) => {
                        console.log(result);
                        if(result.data > 0) {
                            console.log("삭제성공");
                            $(document).on('click',"#resultBtn" + madalIdx ,(e) => {
                                $('#cancleNum'+madalIdx).remove()
                            })
                        }else{
                            console.log("삭제실패");
                        }

                    })
            })


        }
    }
}



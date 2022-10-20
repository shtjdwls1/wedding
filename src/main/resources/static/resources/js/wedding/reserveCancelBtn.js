var madalIdx;

const madal_idx = (target) => {
    madalIdx = target.id.substring(17);
console.log(madalIdx)
    $("#cancelModal1"+madalIdx).modal({backdrop: 'static',keyboard:false});
    $("#cancelModal2"+madalIdx).modal({backdrop: 'static',keyboard:false});

}

const reserve_cancel = (target) => {
    const targetData = target.id;
    console.log("인덱스 확인 ", targetData)
    const updateData = {
        hreserveIdx: targetData,
    }

        axios.post('/myReserve/cancel', updateData)
            .then((result) => {
                console.log(result);
                location.href="/myReserve"
            })

}


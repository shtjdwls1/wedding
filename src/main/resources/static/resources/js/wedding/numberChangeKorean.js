$(() => {
    new numberChangeKorean();
})

export class numberChangeKorean {
    constructor() {
        this.eventBinding()
    }

    eventBinding() {

        function num2han(e) {
            let targetText = e.target
            let targetNum = e.target.innerHTML
            let num = parseInt((targetNum + '').replace(/[^0-9]/g, ''), 10) + '';
            console.log("1111",num)
            if (num == '0') {
                return '영';
            }
            if(!isNaN(num)){
                var number = ['영', '일', '이', '삼', '사', '오', '육', '칠', '팔', '구'];
                var unit = ['', '만', '억', '조'];
                var smallUnit = ['천', '백', '십', ''];
                var result = [];  //변환된 값을 저장할 배열

                var unitCnt = Math.ceil(num.length / 4);
                num = num.padStart(unitCnt * 4, '0')  //4자리 값이 되도록 0을 채운다

                var regexp = /[\w\W]{4}/g;  //4자리 단위로 숫자 분리
                var array = num.match(regexp);

                //낮은 자릿수에서 높은 자릿수 순으로 값을 만든다(그래야 자릿수 계산이 편하다)
                for (var i = array.length - 1, unitCnt = 0; i >= 0; i--, unitCnt++) {
                    var hanValue = makeKorean(array[i]);  //한글로 변환된 숫자
                    if (hanValue == '') { //값이 없을땐 해당 단위의 값이 모두 0이란 뜻.
                        continue;
                    }
                    result.unshift(hanValue + unit[unitCnt]); //unshift는 항상 배열의 앞에 넣는다.
                }

                //여기로 들어오는 값은 무조건 네자리이다. 1234 -> 일천이백삼십사
                function makeKorean(text) {
                    var str = '';
                    for (var i = 0; i < text.length; i++) {
                        var num = text[i];
                        if (num == '0') {//0은 읽지 않는다
                            continue;
                        }
                        str += number[num] + smallUnit[i];
                    }

                    return str;
                }
                console.log("결과확인",result)
                targetText.innerHTML = result.join('')+ '원'
                return result.join('');
            }

        }

        $(document).on('click',".price",(e)=>{
            console.log("금액확인",e.target.innerHTML)
            num2han(e)
        })

    }
}
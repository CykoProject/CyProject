$('#charge_kakao').click(function () {
    var IMP = window.IMP;
    IMP.init('imp34994645');
    var money = $('input[name=money]:checked').val();
    console.log(money);
    if(money > 1000000) {
        alert('충전 금액은 최대 1,000,000원 이하 입니다.')
        return false;
    }
    if(money < 1000) {
        alert('충전 금액은 최소 1,000원 이상 입니다.')
        return false;
    }
    IMP.request_pay({
        pg : "kakaopay",
        merchant_uid : 'merchant_' + new Date().getTime(),
        name : '도토리 충전',
        amount : money
    }, function(rsp) {
        if (rsp.success) {
            var msg = '결제가 완료되었습니다.';
            fetch(`/user/charge?money=${money}`,{
                method : 'post',
                headers: {'Content-type': 'application/json'}
            }).then(res => {
                return res.json();
            })
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
        document.location.href="/user/charge"; //alert창 확인 후 이동할 url 설정
    });
});

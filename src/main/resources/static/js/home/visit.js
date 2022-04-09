const visitElem = document.querySelector('.visit-top-menu');
if(visitElem) {
    const visit_url = new URL(location.href);
    const visit_urlParams = visit_url.searchParams;
    const ihost = parseInt(visit_urlParams.get("iuser"));
    const loginUserPk = parseInt(document.querySelector('.data-pk').dataset.loginuser);

    //============================ 비밀로하기(on) start ==========================
    const visitSecretArr = document.querySelectorAll('.visit-secret');
    visitSecretArr.forEach(item => {
        item.addEventListener('click', (e) => {
            const ivisit = e.target.closest('.visit-elem').dataset.ivisit;
            fetch(`/ajax/home/visit/secret?ivisit=${ivisit}`)
                .then(res => res.json())
                .then(data => {
                    if(data.result) {
                        e.target.closest('#status-bar').className = 'visit-secret-bar';
                        e.target.closest('.secret-elem').remove();
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    });
    //============================ 비밀로하기(on) finish ==========================



    //============================ 삭제 start =============================================
    const visitDelArr = document.querySelectorAll('.visit-del');
    visitDelArr.forEach(item => {
        item.addEventListener('click', (e) => {
            if(!confirm('정말로 삭제하시겠습니까 ?')) {
                return;
            }

            const ivisit = e.target.closest('.visit-elem').dataset.ivisit;
            const iuser = e.target.dataset.iuser;

            fetch(`/ajax/home/visit/del?ivisit=${ivisit}&ihost=${ihost}&iuser=${iuser}`, {
                method : 'DELETE',
                headers : {'Content-Type' : 'application/json'}
            })
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    if(data.result === 1) {
                        e.target.closest('.visit-elem').remove();
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    });
    //============================ 삭제 finish =============================================



    //============================ 수정 start ==================================
    const visitModArr = document.querySelectorAll('.visit-mod');
    visitModArr.forEach(item => {
        item.addEventListener('click', (e) => {
            const ivisit = e.target.closest('.visit-elem').dataset.ivisit;
            const iuser = parseInt(e.target.dataset.iuser);
            if(iuser === loginUserPk || ihost === loginUserPk) {
                location.href = `/home/visit/write?ivisit=${ivisit}&tab=mod&ihost=${ihost}&iuser=${iuser}`;
            }
        });
    });
    //============================ 수정 finish ===================================
}
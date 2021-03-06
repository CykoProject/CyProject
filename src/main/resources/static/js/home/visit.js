mynoteObj.width = '100%';
mynoteObj.height = '100px';

const visitElem = document.querySelector('.visit-container');
if(visitElem) {
    const visit_url = new URL(location.href);
    const visit_urlParams = visit_url.searchParams;
    const ihost = parseInt(visit_urlParams.get("iuser"));
    const loginUserPk = parseInt(document.querySelector('.data-pk').dataset.loginuser);

    //============================ 방명록 작성 start ==========================
    const visitWriteBtn = document.querySelector('.board-btn');
    if(visitWriteBtn) {
        const visitWriteElem = document.querySelector('.visit-write');
        const modalCancelBtn = document.querySelector('#modal-cancel');
        visitWriteBtn.addEventListener('click', (e) => {
            if(visitWriteElem.style.display === '' || visitWriteElem.style.display === 'none') {
                visitWriteElem.style.display = 'block';
            }
        });

        modalCancelBtn.addEventListener('click', () => {
            const ctnt = document.querySelector('.my-note');
            const ctntDiv = document.querySelector('.my-note-div');
            ctnt.value = '';
            ctntDiv.innerHTML = '';
            visitWriteElem.style.display = 'none';

        });

    }

    if(loginUserPk) {
        const insVisitBtn = document.querySelector('.ins-visit');
        insVisitBtn.addEventListener('click', (e) => {
            const visitFrmElem = document.querySelector('.visitFrm');
            const textareaCtnt = visitFrmElem.querySelector('textarea').value;
            if (textareaCtnt.length === 0) {
                e.preventDefault();
                alert('1자 이상 작성해 주세요.');
            }
        });
    }

    //============================ 방명록 작성 finish ==========================


    //============================ 비밀로하기(on) start ==========================
    const visitSecret = () => {
        const visitSecretArr = document.querySelectorAll('.visit-secret');
        visitSecretArr.forEach(item => {
            item.addEventListener('click', (e) => {
                const ivisit = e.target.closest('.visit-elem').dataset.iboard;
                fetch(`/ajax/home/visit/secret?ivisit=${ivisit}`)
                    .then(res => res.json())
                    .then(data => {
                        if(data.result) {
                            e.target.closest('#status-bar').className = 'visit-secret-bar';

                            const superElem = e.target.closest('.secret-elem');
                            superElem.innerHTML = '';
                            const openSpan = document.createElement('span');
                            openSpan.classList.add('visit-open');
                            openSpan.innerText = '공개하기 ';
                            const span = document.createElement('span');
                            span.innerText = '|';
                            superElem.className = 'open-elem';

                            superElem.appendChild(openSpan);
                            superElem.appendChild(span);
                            visitOpen();
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            });
        });
    }
    visitSecret();
    //============================ 비밀로하기(on) finish ==========================

    //============================ 공개하기 start =================================
    const visitOpen = () => {
        const visitOpenArr = document.querySelectorAll('.visit-open');
        visitOpenArr.forEach(item => {
            item.addEventListener('click', (e) => {
                const ivisit = e.target.closest('.visit-elem').dataset.iboard;
                fetch(`/ajax/home/visit/secret?ivisit=${ivisit}`)
                    .then(res => res.json())
                    .then(data => {
                        if(data.result) {
                            e.target.closest('#status-bar').className = 'visit-date-bar';

                            const superElem = e.target.closest('.open-elem');
                            superElem.innerHTML = '';
                            const openSpan = document.createElement('span');
                            openSpan.classList.add('visit-secret');
                            openSpan.innerText = '비밀로하기 ';
                            const span = document.createElement('span');
                            span.innerText = '|';
                            superElem.className = 'secret-elem';

                            superElem.appendChild(openSpan);
                            superElem.appendChild(span);
                            visitSecret();
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            })
        });
    }
    visitOpen();
    //============================ 공개하기 start =================================



    //============================ 삭제 start =============================================
    const visitDelArr = document.querySelectorAll('.visit-del');
    visitDelArr.forEach(item => {
        item.addEventListener('click', (e) => {
            if(!confirm('정말로 삭제하시겠습니까 ?')) {
                return;
            }

            const ivisit = e.target.closest('.visit-elem').dataset.iboard;
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
                        alert('삭제에 성공했습니다 !');
                    }else {
                        alert('삭제에 실패했습니다.');
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
            const parent = e.target.closest('.visit-elem');
            const iboard = parent.querySelector('#data-iboard').dataset.iboard;
            const iuser = e.target.dataset.iuser;
            const ihost = new URL(location.href).searchParams.get('iuser')
            location.href = `/home/visit/mod?iuser=${ihost}&writer=${iuser}&iboard=${iboard}`;
        });
    });
    //============================ 수정 finish ===================================

    //============================ 댓글 start ===================================
    commentObj.parentName = '.visit-elem';
    commentObj.menu = 'visit';
    commentObj.size = 5;
    commentObj.init();
    commentObj.makeCnt();

    commentObj.writeCmt.init.execute('.ctnt-btn');
    commentObj.writeCmt.submit();

    const commentCountElemArr = document.querySelectorAll('.comment-cnt');
    commentCountElemArr.forEach(item => {
        item.addEventListener('click', () => {
            const parent = item.closest('.comment-container');
            const ctntElem = parent.querySelector('.hidden-ctnt');
            if(ctntElem.style.display === 'none' || ctntElem.style.display === '') {
                ctntElem.style.display = 'block';
            } else {
                ctntElem.style.display = 'none';
            }
        });
    });
    //============================ 댓글 finish ===================================
}

const visitWriteElem = document.querySelector('.visit-write');
if(visitWriteElem) {
    const visitFrm = document.querySelector('.visitFrm');
    visitFrm.addEventListener('submit', (e) => {
        const ctntVal = visitFrm.querySelector('textarea').value;
        if(ctntVal.length === 0) {
            e.preventDefault();
        }
    });
}

const selectMinimeElem = document.querySelector('.select-minime');
if(selectMinimeElem) {
    const minimeImgElem = document.querySelector('.visit-minime');
    selectMinimeElem.addEventListener('change', () => {
        const url = '/pic/minime/'
        const selectedElem = selectMinimeElem[selectMinimeElem.selectedIndex];
        const file = selectedElem.dataset.file !== '0' ? url + selectedElem.dataset.file : '/static/img/defaultProfileImg.jpeg';
        minimeImgElem.src = file;
    });
}
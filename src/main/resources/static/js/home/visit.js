const visitElem = document.querySelector('.visit-container');
if(visitElem) {
    const visit_url = new URL(location.href);
    const visit_urlParams = visit_url.searchParams;
    const ihost = parseInt(visit_urlParams.get("iuser"));
    const loginUserPk = parseInt(document.querySelector('.data-pk').dataset.loginuser);

    //============================ 방명록 작성 start ==========================
    const insVisitBtn = document.querySelector('.ins-visit');
    insVisitBtn.addEventListener('click', (e) => {
        const visitFrmElem = document.querySelector('.visitFrm');
        const textareaCtnt = visitFrmElem.querySelector('textarea').value;
        if(textareaCtnt.length === 0) {
            e.preventDefault();
            alert('1자 이상 작성해 주세요.');
        }
    });
    //============================ 방명록 작성 finish ==========================


    //============================ 비밀로하기(on) start ==========================
    const visitSecret = () => {
        const visitSecretArr = document.querySelectorAll('.visit-secret');
        visitSecretArr.forEach(item => {
            item.addEventListener('click', (e) => {
                const ivisit = e.target.closest('.visit-elem').dataset.ivisit;
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
                const ivisit = e.target.closest('.visit-elem').dataset.ivisit;
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
    let cnt = 0;
    visitModArr.forEach(item => {
        item.addEventListener('click', () => {
            const superElem = item.closest('.visit-elem');
            if(cnt === 1) {
                const visitContentsElem = document.querySelector('.visit-contents');
                const preBox = visitContentsElem.querySelector('.mod-area');
                const curBox = superElem.querySelector('.mod-area');
                preBox.remove();
                if(curBox) {
                    cnt = 0;
                    return;
                }
            }
            cnt = 1;
            const ivisit = superElem.dataset.ivisit;
            const visitCtntElem = superElem.querySelector('.visit-ctnt');

            const insertVisit = (data) => {
                const visitData = {
                    ivisit : data.ivisit,
                    ihost : data.ihost,
                    ctnt : data.ctnt.replaceAll("\n", "\r\n"),
                    iuser : data.iuser,
                    secret : data.secret
                }

                fetch(`/ajax/home/visit/mod`, {
                    method : 'POST',
                    headers : {'Content-Type' : 'application/json'},
                    body : JSON.stringify(visitData)
                })
                    .then(res => res.json())
                    .then(data => {
                        if(data.result === 1) {
                            location.reload();
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }

            const makeVisitMod = (data) => {
                const div = document.createElement('div');
                div.classList.add('mod-area');
                const btnDiv = document.createElement('div');
                const textArea = document.createElement('textarea');
                const visitModBtn = document.createElement('button');
                visitModBtn.classList.add('visit-mod-btn');
                visitModBtn.innerText = '저장';
                textArea.value = data.ctnt.replaceAll("<br>", "\r\n");
                textArea.classList.add('visit-textarea');
                div.appendChild(textArea)
                btnDiv.appendChild(visitModBtn);
                div.appendChild(btnDiv);
                visitCtntElem.appendChild(div);
                visitModBtn.addEventListener('click', () => {
                    console.log(data.ctnt)
                    data.ctnt = textArea.value;
                    if(data.ctnt.length === 0) {
                        alert('1자 이상 작성해 주세요');
                        return;
                    }
                    insertVisit(data);
                })
            }

            fetch(`/ajax/home/visit/mod?ivisit=${ivisit}`)
                .then(res => res.json())
                .then(data => {
                    makeVisitMod(data);
                })
                .catch(e => {
                    console.error(e);
                });
        });
    });
    //============================ 수정 finish ===================================
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
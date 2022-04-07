// todo 신고, 수정


const visitElem = document.querySelector('.visit-top-menu');
if(visitElem) {
    const visit_url = new URL(location.href);
    const visit_urlParams = visit_url.searchParams;
    const ihost = parseInt(visit_urlParams.get("iuser"));
    const loginUserPk = parseInt(document.querySelector('.data-pk').dataset.loginuser);

    const getList = (ihost) => {
        fetch(`/ajax/home/visit?ihost=${ihost}`)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                setList(data);
            })
            .catch(e => {
                console.error(e);
            });
    }

    const visitContentsElem = document.querySelector('.visit-contents');
    const setList = (list) => {
        if(list.length === 0) {
            visitContentsElem.innerHTML = `<strong>방명록을 남겨주세요 !</strong>`;
        }
        list.forEach(item => {
            const rdtArr = item.rdt.split("T"); // [YYYY-MM-DD, HH:MM:SS]
            const divElem = document.createElement('div');
            if (item.secret) {
                if (loginUserPk === ihost || loginUserPk === item.iuser.iuser) {
                    divElem.innerHTML = `
                        <div class="visit-secret-bar">
                            <div>
                                <span>no.${item.ivisit} (${rdtArr[0]} ${rdtArr[1]})</span>
                                <span>${item.iuser.nm}</span>
                                <span>${item.iuser.iuser}</span>
                            </div>
                            <div>
                                <span class="visit-del">삭제</span>
                                <span>|</span>
                                <span class="visit-mod">수정</span>
                                <span>|</span>
                                <span class="visit-report">신고</span>
                            </div>
                        </div>
                        <div class="visit-ctnt">
                        <!--        TODO img 삽입                -->
                        
                            <span>${item.ctnt}</span>
                        </div>
                    `;
                } else {
                    divElem.innerHTML = `
                        <div class="visit-secret-bar">
                            <div>
                                <span>no.${item.ivisit} (${rdtArr[0]} ${rdtArr[1]})</span>
                                <span>${item.iuser.nm}</span>
                            </div>
                            <div>
                                <span class="visit-report">신고</span>
                            </div>
                        </div>
                        <div class="visit-ctnt">
                        <!--        TODO img 삽입                -->
                        
                            <span class="secret-ctnt">비밀이야 (이 글은 홈 주인과 작성자만 볼 수 있어요.)</span>
                        </div>
                    `;
                }
            } else {
                if (loginUserPk === item.iuser.iuser) {
                    divElem.innerHTML = `
                        <div class="visit-date-bar">
                            <div>
                                <span>no.${item.ivisit} (${rdtArr[0]} ${rdtArr[1]})</span>
                                <span>${item.iuser.nm}</span>
                            </div>
                            <div>
                                <span class="visit-secret" data-ivisit="${item.ivisit}">비밀로하기</span>
                                <span class="quater">|</span>
                                <span class="visit-del">삭제</span>
                                <span>|</span>
                                <span class="visit-mod">수정</span>
                                <span>|</span>
                                <span class="visit-report">신고</span>
                            </div>
                        </div>
                        <div class="visit-ctnt">
                        <!--        TODO img 삽입                -->
                        
                            <span>${item.ctnt}</span>
                        </div>
                    `;
                } else if(loginUserPk === ihost) {
                    divElem.innerHTML = `
                        <div class="visit-date-bar">
                            <div>
                                <span>no.${item.ivisit} (${rdtArr[0]} ${rdtArr[1]})</span>
                                <span>${item.iuser.nm}</span>
                            </div>
                            <div>
                                <span class="visit-del">삭제</span>
                                <span>|</span>
                                <span class="visit-mod">수정</span>
                                <span>|</span>
                                <span class="visit-report">신고</span>
                            </div>
                        </div>
                        <div class="visit-ctnt">
                        <!--        TODO img 삽입                -->
                        
                            <span>${item.ctnt}</span>
                        </div>
                    `;
                } else {
                    divElem.innerHTML = `
                        <div class="visit-date-bar">
                            <div>
                                <span>no.${item.ivisit} (${rdtArr[0]} ${rdtArr[1]})</span>
                                <span>${item.iuser.nm}</span>
                            </div>
                            <div>
                                <span class="visit-report">신고</span>
                            </div>
                        </div>
                        <div class="visit-ctnt">
                        <!--        TODO img 삽입                -->
                        
                            <span>${item.ctnt}</span>
                        </div>
                    `;
                }
            }
            visitContentsElem.appendChild(divElem);

            //============================ 비밀로하기(on) start ==========================
            const onSecret = divElem.querySelector('.visit-secret');
            if(onSecret !== null) {
                onSecret.addEventListener('click', () => {
                    const data = {
                        ivisit : item.ivisit,
                        ihost : ihost,
                        ctnt : item.ctnt,
                        iuser : item.iuser,
                        secret : true
                    }

                    fetch("/ajax/home/visit/secret", {
                        method : "POST",
                        headers : {'Content-Type' : 'application/json'},
                        body : JSON.stringify(data)
                    })
                        .then(res => res.json())
                        .then(data => {
                            console.log(data);
                            if(data.result === 1) {
                                const visitBar = onSecret.closest('.visit-date-bar');
                                const quater = visitBar.querySelector('.quater');
                                visitBar.className = 'visit-secret-bar';
                                quater.remove();
                                onSecret.remove();
                            }
                        })
                        .catch(e => {
                            console.error(e);
                        });
                });
            }
            //============================ 비밀로하기(on) finish ==========================



            //============================ 삭제 start =============================================
            const visitDel = divElem.querySelector('.visit-del');
            if(visitDel) {
                visitDel.addEventListener('click', () => {
                    if(loginUserPk === item.iuser.iuser || loginUserPk === ihost) {
                        if(confirm('정말로 삭제하시겠습니까?')) {
                            fetch(`/ajax/home/visit/del?ivisit=${item.ivisit}&iuser=${item.iuser.iuser}&ihost=${ihost}`)
                                .then(res => res.json())
                                .then(data => {
                                    if(data.result === 1) {
                                        divElem.remove();
                                        if(visitContentsElem.children.length === 0) {
                                            visitContentsElem.innerHTML = `<strong>방명록을 남겨주세요 !</strong>`;
                                        }
                                    }
                                })
                                .catch(e => {
                                    console.error(e);
                                });
                        }
                    } else {
                        alert('처리할 수 없습니다.');
                    }
                });
            }
            //============================ 삭제 finish =============================================

            //============================ 수정 start ==================================
            const visitModBtn = divElem.querySelector('.visit-mod');
            if(visitModBtn) {
                visitModBtn.addEventListener('click', () => {
                    location.href = `/home/visit/write?ivisit=${item.ivisit}&iuser=${ihost}&tab=mod`;
                });
            }

            //============================ 수정 finish ===================================
        });
    }
    getList(ihost);
}
const visitElem = document.querySelector('.visit-container');
if(visitElem) {
    const visit_url = new URL(location.href);
    const visit_urlParams = visit_url.searchParams;
    const ihost = parseInt(visit_urlParams.get("iuser"));
    const loginUserPk = parseInt(document.querySelector('.data-pk').dataset.loginuser);

    //============================ 방명록 작성 start ==========================
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
        // 글꼴변경
        const fontElem = document.querySelector('.font-select');
        let preFontVal = '';
        fontElem.addEventListener('change', () => {
            const selectedOptionElem = fontElem[fontElem.selectedIndex];
            const fontFileVal = selectedOptionElem.dataset.font;
            const visitTextAreaElem = document.querySelector('.visit-textarea');
            if(preFontVal != '') {
                visitTextAreaElem.classList.remove(preFontVal);
            }
            visitTextAreaElem.classList.add(fontFileVal);
            preFontVal = fontFileVal;
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
    let cnt = 0;
    visitModArr.forEach(item => {
        item.addEventListener('click', () => {
            const superElem = item.closest('.visit-elem');

            if(cnt === 1) {
                const visitContentsElem = document.querySelector('.visit-contents');
                const preBox = visitContentsElem.querySelector('.mod-area');
                const curBox = superElem.querySelector('.mod-area');
                if(preBox) {
                    preBox.remove();
                }
                if(curBox) {
                    cnt = 0;
                    return;
                }
            }
            cnt = 1;
            const ivisit = superElem.dataset.iboard;
            const visitCtntElem = superElem.querySelector('.visit-mod-wrap');

            const insertVisit = (data, font) => {
                let ifont = null;
                if(parseInt(font) !== 0) {
                    ifont = {item_id : parseInt(font)};
                }
                const visitData = {
                    ivisit : data.ivisit,
                    ihost : data.ihost,
                    ctnt : data.ctnt.replaceAll("\n", "\r\n"),
                    iuser : data.iuser,
                    secret : data.secret,
                    iminime : data.iminime,
                    ifont : ifont
                }

                console.log(visitData);

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

            const getUserFontList = (iuser, modData) => {
                fetch(`/ajax/home/font?iuser=${iuser}`)
                    .then(res => res.json())
                    .then(data => {
                        setVisitModElem(data, modData);
                    })
                    .catch(e => {
                        console.error(e);
                    });

            }

            const selFont = (selectElem, textarea) => {
                selectElem.addEventListener('change', () => {
                    const fontFile = selectElem[selectElem.selectedIndex].dataset.file;

                    let preFont = '';
                    textarea.classList.forEach(item => {
                        if(item !== 'visit-textarea') {
                            preFont = item;
                        }
                    });
                    if(preFont !== '') {
                        textarea.classList.remove(preFont);
                    }
                    if(fontFile !== undefined) {
                        textarea.classList.add(fontFile);
                    }
                });
            }

            const setVisitModElem = (fontData, data) => {
                const div = document.createElement('div');
                div.classList.add('mod-area');

                const selectElem = document.createElement('select');
                selectElem.classList.add('select-font')
                const option = document.createElement('option');
                option.value = 0;
                option.innerText = '글꼴선택';
                selectElem.appendChild(option);
                fontData.forEach(item => {
                    const option = document.createElement('option');
                    option.value = item.item_id.item_id;
                    option.innerText = item.item_id.nm;
                    option.dataset.file = item.item_id.file;

                    selectElem.appendChild(option);
                });

                div.appendChild(selectElem);

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
                    data.ctnt = textArea.value;
                    if(data.ctnt.length === 0) {
                        alert('1자 이상 작성해 주세요');
                        return;
                    }
                    const font = selectElem[selectElem.selectedIndex].value;
                    insertVisit(data, font);
                });

                selFont(selectElem, textArea);
            }

            const makeVisitMod = (data) => {
                getUserFontList(data.iuser.iuser, data);
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
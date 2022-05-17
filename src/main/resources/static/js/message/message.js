const msgAllChkBtn = document.querySelector('.msg-all-chk');
const chkArr = document.querySelectorAll('.msg-chk');
let cnt = 0;

if(msgAllChkBtn) {
    msgAllChkBtn.addEventListener('click', () => {
        const status = msgAllChkBtn.checked;
        chkArr.forEach(item => {
            item.checked = status;
            cnt = status === true ? chkArr.length : 0;
        });
    });
    chkArr.forEach(item => {
        item.addEventListener('click', () => {
            let chkCnt = chkArr.length;
            let status = item.checked;

            cnt = status === true ? ++cnt : --cnt;
            msgAllChkBtn.checked = cnt === chkCnt ? true : false;
        });
    });
}

const msgBox = document.querySelector('.msgbox');
if(msgBox) {
    // detail 이동
    const msgTrArr = document.querySelectorAll('.msg-tr');
    msgTrArr.forEach(item => {
        item.addEventListener('click', () => {
            const imsg = item.closest('.msg-data').dataset.imsg;
            const box = document.querySelector('.msgbox').dataset.box;
            const popupWidth = 400;
            const popupHeight = 500;
            const popX = (window.screen.width / 2) - (popupWidth / 2);
            const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
            const option = `width = ${popupWidth}px
                , height = ${popupHeight}px
                , left = ${popX}
                , top = ${popY}
                , scrollbars = no
            `;
            item.closest('.msg-data').classList.replace('unread-msg', 'read-msg');
            if(box === 'savebox') {
                window.open(`/msg/detail?imsg=${imsg}&box=save`, 'msg', option);
            } else {
                window.open(`/msg/detail?imsg=${imsg}`, 'msg', option);
            }
        });
    });

    // 삭제
    const msgDelBtn = document.querySelector('.msg-del');
    if(msgDelBtn) {
        msgDelBtn.addEventListener('click', () => {
            const chkBoxArr = document.querySelectorAll('.msg-chk');
            const imsgArr = [];
            chkBoxArr.forEach(item => {
                if (item.checked) {
                    imsgArr.push(item.closest('.msg-data').dataset.imsg);
                }
            });
            if (imsgArr.length === 0) return;
            if (!confirm("선택한 쪽지를 삭제하시겠습니까?")) return;

            fetch(`/ajax/msg/del`, {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(imsgArr)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.result === 1) {
                        location.reload();
                    } else {
                        alert('삭제에 실패했습니다.');
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    }
    const msgCheckElem = document.querySelector('.msg-check');
    if(msgCheckElem) {
        msgCheckElem.addEventListener('click', () => {
            const chkBoxArr = document.querySelectorAll('.msg-chk');
            const imsgArr = [];
            chkBoxArr.forEach(item => {
                if (item.checked) {
                    imsgArr.push(item.closest('.msg-data').dataset.imsg);
                }
            });
            if (imsgArr.length === 0) return;

            fetch('/ajax/msg/check', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify(imsgArr)
            })
                .then(res => res.json())
                .then(data => {
                    if (data.result === 1) {
                        location.reload();
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    }
}


const msgWrite = document.querySelector('.msg-write');
if(msgWrite) {
    const friendsAllChk = document.querySelector('.friends-all-chk');
    const friendsChkArr = document.querySelectorAll('.friends-chk');
    const cntLength = friendsChkArr.length;

    let chkCnt = 0;
    friendsAllChk.addEventListener('click', () => {
        const chkStatus = friendsAllChk.checked;
        friendsChkArr.forEach(item => {
            item.checked = chkStatus;
            chkCnt = chkStatus === true ? cntLength : 0;
        });
    });

    friendsChkArr.forEach(item => {
        item.addEventListener('click', () => {
            chkCnt = item.checked === true ? ++chkCnt : --chkCnt;
            friendsAllChk.checked = cntLength === chkCnt ? true : false;
        });
    });

    const msgSubmitBtn = document.querySelector('.msg-submit-btn');
    msgSubmitBtn.addEventListener('click', () => {
        const friendsChkArr = document.querySelectorAll('.friends-chk');
        let checkedFriend = [];
        friendsChkArr.forEach(item => {
            if(item.checked) {
                const receiver = item.closest('.friends-data').dataset.receiver;
                checkedFriend.push(receiver);
            }
        });
        const data = {
            'ctnt' : document.querySelector('.cus-textarea').value,
            'receiver' : checkedFriend
        }
        if(checkedFriend.length === 0)  { alert('친구를 선택해주세요 !'); return; }

        fetch('/ajax/msg/write', {
            method : "POST",
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                if(data === 1) {
                    location.href = '/msg/outbox';
                }
            })
            .catch(e => {
                console.error(e);
            });
    });
}

const msgDetailContainer = document.querySelector('.msg-detail-container');
if(msgDetailContainer) {
    const msgReplyBtnElem = msgDetailContainer.querySelector('#msg-reply-btn');
    const msgReplyContainerElem = msgDetailContainer.querySelector('.msg-reply-container');
    const msgCloseBtnElem = msgDetailContainer.querySelector('#msg-close');
    const msgDelBtnElem = msgDetailContainer.querySelector('#msg-del-btn');
    const imsg = msgDelBtnElem.dataset.imsg;
    const iuser = loginUserPk.dataset.iuser;


    const ws = new WebSocket("ws://localhost:8090/ws");
    console.log('연결성공');
    ws.onopen = onOpen;
    ws.onmessage = onMessage;

    function onOpen(evt) {
        var str = "open=" + iuser;
        ws.send(str);
    }

    function onMessage(msg) {

    }


    const msgBtn = document.querySelector('#msg-reply-submit');
    if (msgBtn) {
        msgBtn.addEventListener('click', () => {
            console.log('asd');
            const ctnt = document.querySelector('.msg-reply-text').value;
            const receiverArr = [];
            receiverArr.push(document.querySelector('#sender').dataset.sender);

            ws.send(`msg={"receiver" : "${receiverArr}", "iuser":${iuser}, "ctnt":"${ctnt}"}`);
        });
    }

    const msgUrl = new URL(window.location.href);
    const box = msgUrl.searchParams.get("box");

    const msgSendSuccess = (msg) => {
        const div = document.createElement('div');
        div.classList.add('msg-send-success');
        div.innerHTML = `
            <span>${msg}</span>
        `;
        window.document.body.appendChild(div);
        setTimeout(() => {
            div.remove();
        }, 3000);
    }

    msgCloseBtnElem.addEventListener('click', () => {
        window.close();
    });

    if(box !== 'save') {
        msgDelBtnElem.addEventListener('click', () => {
            if (confirm('정말로 삭제하시겠습니까?')) {
                fetch(`/ajax/msg/del?imsg=${imsg}`)
                    .then(res => res.json())
                    .then(data => {
                        if (data.result === 1) {
                            opener.location.reload();
                            window.close();
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }
        });
    } else {
        msgDelBtnElem.addEventListener('click', () => {
            if(confirm(`보관함에서만 삭제됩니다.\n정말로 삭제하시겠습니까?`)) {
                fetch(`/ajax/msg/savebox/del?imsg=${imsg}&iuser=${iuser}`)
                    .then(res => res.json())
                    .then(data => {
                        if (data.result === 1) {
                            opener.location.reload();
                            window.close();
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }
        });
    }

    if(msgReplyBtnElem) {
        msgReplyBtnElem.addEventListener('click', () => {
            if(msgReplyContainerElem.style.visibility === 'hidden' || msgReplyContainerElem.style.visibility === '') {
                msgReplyContainerElem.style.visibility = 'visible';
            } else {
                msgReplyContainerElem.style.visibility = 'hidden';
            }
        });
    }
    const msgReplySubmitBtnELem = msgDetailContainer.querySelector('#msg-reply-submit');
    msgReplySubmitBtnELem.addEventListener('click', () => {
        const ctnt = msgDetailContainer.querySelector('.msg-reply-text').value;
        const receiver = parseInt(msgDetailContainer.querySelector('#sender').dataset.sender);
        const receiverArr = [];
        receiverArr.push(receiver);
        const iuser = parseInt(msgDetailContainer.dataset.iuser);
        const data = {
            "iuser" : iuser,
            "receiver" : receiverArr,
            "ctnt" : '[답장] ' + ctnt
        }
        fetch('/ajax/msg/write', {
            method : "POST",
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                if(data === 1) {
                    msgSendSuccess('답장을 성공적으로 보냈습니다 !');
                    msgReplyContainerElem.style.visibility = 'hidden';
                }
            })
            .catch(e => {
                console.error(e);
            });
    });

    const msgSaveBox = document.querySelector('.msg-savebox');
    if(msgSaveBox) {
        msgSaveBox.addEventListener('click', () => {
            const imsg = parseInt(msgSaveBox.dataset.imsg);
            const iuser = parseInt(loginUserPk.dataset.iuser);
            const data = {
                "imsg" : imsg,
                "iuser" : iuser
            }

            fetch('/ajax/msg/savebox', {
                method : 'POST',
                headers : {'Content-Type' : 'application/json'},
                body : JSON.stringify(data)
            })
                .then(res => res.json())
                .then(data => {
                    if(data.result === 1) {
                        msgSendSuccess('보관되었습니다 !');
                    } else {
                        msgSendSuccess('이미 보관되어있습니다.');
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    }
}

const msgSaveDelBtnElem = document.querySelector('.msg-save-del');
if(msgSaveDelBtnElem) {
    msgSaveDelBtnElem.addEventListener('click', () => {
        const imsgArr = [];
        chkArr.forEach(item => {
            if(item.checked) {
                const imsg = item.closest('.msg-data').dataset.imsg;
                imsgArr.push(imsg);
            }
        });
        if(imsgArr.length === 0) return;
        if(!confirm('정말로 삭제하시겠습니까?')) return;
        const data = {
            iuser : loginUserPk.dataset.iuser,
            imsg : imsgArr
        };
        fetch('/ajax/msg/savebox/del', {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                if(data.result === 1) {
                    window.location.reload();
                }
            })
            .catch(e => {
                console.error(e);
            });
    });
}
const messageContainer = document.querySelector('.msgbox');
if(messageContainer) {
    // detail 이동
    const msgTrArr = document.querySelectorAll('.msg-tr');
    msgTrArr.forEach(item => {
        item.addEventListener('click', () => {
            const imsg = item.closest('.msg-data').dataset.imsg;
            const popupWidth = 500;
            const popupHeight = 300;
            const popX = (window.screen.width / 2) - (popupWidth / 2);
            const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
            const option = `width = ${popupWidth}px
                , height = ${popupHeight}px
                , left = ${popX}
                , top = ${popY}
                , scrollbars = no
            `;
            item.closest('.msg-data').classList.replace('unread-msg', 'read-msg');
            window.open(`/msg/detail?imsg=${imsg}`, 'msg', option);
        });
    });

    // 체크박스 관련
    const msgAllChkBtn = document.querySelector('.msg-all-chk');
    const chkArr = document.querySelectorAll('.msg-chk');
    msgAllChkBtn.addEventListener('click', () => {
        const status = msgAllChkBtn.checked;
        chkArr.forEach(item => {
            item.checked = status;
        });
    });
    let cnt = 0;
    chkArr.forEach(item => {
        item.addEventListener('click', () => {
            let chkCnt = chkArr.length;
            let status = item.checked;

            cnt = status === true ? ++cnt : --cnt;
            console.log(status);
            console.log(cnt, chkCnt);
            msgAllChkBtn.checked = cnt === chkCnt ? true : false;
        });
    });
}


const msgWrite = document.querySelector('.msg-write');
if(msgWrite) {
    const friendListElem = document.querySelector('.friend-list');
    const friendTableElem = document.querySelector('.friend-table');

    friendListElem.addEventListener('click', () => {
        if(friendTableElem.classList.contains('hidden')) {
            friendTableElem.classList.remove('hidden');
        } else {
            friendTableElem.classList.add('hidden');
        }
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
        fetch('/ajax/msg/write', {
            method : "POST",
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                if(data === 1) {
                    location.href = '/msg';
                }
            })
            .catch(e => {
                console.error(e);
            });
    });
}
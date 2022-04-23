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
            'ctnt' : 'aaa',
            'receiver' : checkedFriend
        }
        fetch('/ajax/msg/write', {
            method : "POST",
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
            })
            .catch(e => {
                console.error(e);
            });
    });
}
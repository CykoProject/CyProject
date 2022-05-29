const boardListElem = document.querySelector('.board-list');
const boardMenus = boardListElem.querySelectorAll('.board-menu');

let index = 1;

const makeMenuProperties = (elem, text, name) => {
    elem.innerText = text;
    elem.href = `/home/${name}?iuser=${iuser}`;
    boardMenus[index].appendChild(elem);
    index++;
}

const addBoardMenu = (data) => {
    if (data.diary) {
        makeMenuProperties(createAElem(), '다이어리', 'diary');
    }

    if (data.photo) {
        makeMenuProperties(createAElem(), '사진첩', 'photo');
    }

    if (data.visit) {
        makeMenuProperties(createAElem(), '방명록', 'visit');
    }

    if (data.jukebox) {
        makeMenuProperties(createAElem(), '주크박스', 'jukebox');
    }

    if (data.mini_room) {
        makeMenuProperties(createAElem(), '미니룸', 'miniroom');
    }

    index = 1;
}

fetch(`/ajax/home?iuser=${iuser}`)
    .then(res => res.json())
    .then(data => {
        console.log(data);
        addBoardMenu(data);
    })
    .catch(e => {
        console.log(e);
    });

console.log(iuser);
console.log(loginUserPk);

/* 일촌평 */
const friendCommentForm = document.querySelector('#friend-comment-form');
const friendCommentListElem = document.querySelector('.friend-comment-list');

const getFriendCommentList = () => {
    const homeData = {
        ihost: loginUserPk
    }

    fetch(`/ajax/home/friendComment?iuser=${iuser}`)
        .then(res => res.json())
        .then(data => {
            console.log(data);
            makeFriendCommentList(data);
        })
        .catch(e => {
            console.log(e);
    })
}

getFriendCommentList();



const makeFriendCommentList = list => {
    list.forEach(item => {
        const divElem = document.createElement('div');
        const ulElem = document.createElement('ul');
        const liElem = document.createElement('li');

        const emptyDivElem = document.createElement('div');

        ulElem.innerHTML = `
                <li class="message">${item.ctnt}</li>
                <li class="writer">(${item.nickname} <a href="/home?iuser=${item.writer}">${item.nm}</a>)
                </li>
                <li class="rdt">${item.rdt}</li>
            `;

        ulElem.appendChild(emptyDivElem);
        divElem.appendChild(ulElem);
        friendCommentListElem.appendChild(divElem);

        // 삭제
        if (item.writer == loginUserPk || item.ihost == loginUserPk) {
            const delBtn = document.createElement('input');
            delBtn.type = 'button';
            delBtn.value = 'x';
            delBtn.addEventListener('click', () => {
                if (confirm('삭제 하시겠습니까?')) {
                    delFriendComment(item.imsg, divElem);
                }
            })

            emptyDivElem.appendChild(delBtn);
        }

        return divElem;
    })
}

// 일촌평 삭제
const delFriendComment = (imsg, elem) => {
    fetch(`/ajax/home/friendComment/del/?imsg=${imsg}`, {
        method : 'DELETE',
        headers : {'Content-Type' : 'application/json'}
    })
        .then(res => res.json())
        .then(data => {
            if(data.result === 1) {
                elem.remove();
            } else {
                alert('댓글을 삭제할 수 없습니다.');
            }
        })
        .catch(e => {
            console.error(e);
        });
}

// 일촌평 작성
if (friendCommentForm) {

    const friendCommentFormBtn = friendCommentForm.querySelector('input[name="save"]');
    const friendCommentFormIhost = friendCommentForm.querySelector('input[name="ihost"]');
    const friendCommentFormCtnt = friendCommentForm.querySelector('input[name="ctnt"]');

    /* 일촌평 작성 */
    friendCommentFormBtn.addEventListener('click', () => {

        console.log('click');
        const param = {
            ihost: friendCommentFormIhost.value,
            ctnt: friendCommentFormCtnt.value
        }

        console.log(param);

        fetch('/ajax/home/friendComment/write', {
            method: 'POST',
            headers : { 'Content-Type' : 'application/json' },
            body : JSON.stringify(param)
        })
            .then(res => res.json())
            .then(data => {
                switch (data.result) {
                    case 0 :
                        alert('일촌평 등록에 실패 했습니다. 잠시 후 다시 시도해주세요.');
                        break;
                    case 1 :
                        friendCommentFormCtnt.value = null;
                        location.reload();
                        break;
                }
            })
            .catch(e => {
                console.log(e);
            });
    })
}
const isPopup = opener !== null ? true : false;
if(!isPopup) {
    location.href = '/error/home';
}



const popupWidth = 330;
const popupHeight = 300;
const popX = 1195;
const popY = (window.screen.height / 2) - (popupHeight / 2) - 250;
const aurl = new URL(location.href);
const aparams = aurl.searchParams;
const pk = aparams.get("iuser");
const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
`;

const tab_list_css = [
    'tab3','tab4', 'tab5', 'tab6', 'tab7', 'tab8'
];

const tabMenuElem = document.querySelector('.tab-menu');

const url = new URL(window.document.location.href);
const urlParams = url.searchParams;
const iuser = urlParams.get('iuser');
const loginUserPk = document.querySelector('.home-common-pk').dataset.pk;
let cnt = 0;

const createAElem = () => {
    return document.createElement('a');
}
const makeProperties = (elem, text, name) => {
    elem.classList.add(tab_list_css[cnt]);
    elem.innerText = text;
    elem.classList.add(name);
    elem.href = `/home/${name}?iuser=${iuser}`;
    tabMenuElem.appendChild(elem);
    cnt++;
}
const addTabMenu = (data) => {
    if(data.diary) {
        makeProperties(createAElem(), '다이어리', 'diary');
    }
    if(data.photo) {
        makeProperties(createAElem(), '사진첩', 'photo');
    }
    if(data.visit) {
        makeProperties(createAElem(), '방명록', 'visit');
    }
    if(data.jukebox) {
        makeProperties(createAElem(), '주크박스', 'jukebox')
    }
    if(data.mini_room) {
        makeProperties(createAElem(), '미니룸', 'miniroom')
    }
    if(loginUserPk === iuser) {
        makeProperties(createAElem(), '관리', 'manage');
    }
    cnt = 0;

    let pathName = url.pathname.substr(6);

    pathName = pathName.substr(0, pathName.indexOf('/') === -1 ? pathName.length : pathName.indexOf("/"));

    if(pathName.length === 0) {
        document.querySelector('.home').classList.add('menu-checked');
    } else {
        document.querySelector(`.${pathName}`).classList.add('menu-checked');
    }

}
fetch(`/ajax/home?iuser=${iuser}`)
    .then(res => res.json())
    .then(data => {
        console.log(data);
        addTabMenu(data);
        homeCnt(data);
        homeName(data);
    })
    .catch(e => {
        console.error(e);
    });


const msgSendSuccess = (msg) => {
    const div = document.createElement('div');
    div.classList.add('msg-send-success');
    div.innerHTML = `
            <span>${msg}</span>
        `;
    window.document.body.appendChild(div);
    let setTimeOut = setTimeout(() => {
        div.remove();
    }, 3000);

    div.addEventListener('mouseover', () => {
        clearTimeout(setTimeOut);
    });

    div.addEventListener('mouseout', () => {
        setTimeOut = setTimeout(() => {
            div.remove();
        }, 3000);
    });
}

const audioPlayElem = document.querySelector('.audio-section');
let audioPopup;
audioPlayElem.addEventListener('click', () => {
    audioPopup = window.open(`/home/audio?iuser=${pk}`, 'audio', option);
});

/* 방문자 수 */
const homeCnt = (data) => {
    const ctnElem = document.querySelector('.cnt');
    ctnElem.innerHTML = `
        <p>TODAY : ${data.daily_visit} | TOTAL : ${data.total_visit}</p>
    `;
}

/* 홈 이름 */
const homeName = (data) => {
    const titleElem = document.querySelector('.title');
    titleElem.innerHTML = `
        <h1>
            <a href="/home?iuser=${data.iuser}">${data.home_nm}</a>
        </h1>
    `

    if (data.iuser == loginUserPk) {
        const modBtn = document.createElement('input');
        modBtn.type = 'button';
        modBtn.value = '수정';
        modBtn.classList.add('modBtn');

        modBtn.addEventListener('click', () => {
            const modInput = document.createElement('input');
            modInput.value = data.home_nm;

            const saveBtn = document.createElement('input')
            saveBtn.type = 'button';
            saveBtn.value = '저장';

            saveBtn.addEventListener('click', () => {
                const param = {
                    ihome: data.ihome,
                    home_nm: modInput.value
                }
                fetch('/ajax/home/nm/mod', {
                    method: 'PUT',
                    headers: {'Content-Type': 'application/json'},
                    body: JSON.stringify(param)
                })
                    .then(res => res.json())
                    .then(result => {
                        switch (result.result) {
                            case 0:
                                alert('이름 변경에 실패하였습니다.')
                                break;
                            case 1:
                                homeName(data);
                                location.reload();
                                break;
                        }
                    })
                    .catch(e => {
                        console.log(e);
                    });
            });

            const cancelBtn = document.createElement('input');
            cancelBtn.type = 'button';
            cancelBtn.value = '취소';
            cancelBtn.addEventListener('click', () => {
                titleElem.innerText = `${data.home_nm}`;
                homeName(data);
            });

            titleElem.innerHTML = null;
            titleElem.appendChild(modInput);
            titleElem.appendChild(saveBtn);
            titleElem.appendChild(cancelBtn);

        });

        titleElem.appendChild(modBtn);
    }


    return titleElem;
}

/* 프로필 */
fetch(`/ajax/home/profile?iuser=${iuser}`)
.then(res => res.json())
.then(data => {
    console.log(data);
    makeElem(data);
})
.catch(e => {
    console.log(e);
})

const makeElem = (data) => {
    const profileCont = document.querySelector('.profile-container');

    const profileImg = document.querySelector('.profile-img');
    const profileCtnt = document.querySelector('.profile-ctnt');
    const profileName = document.querySelector('.profile-name');


        profileImg.innerHTML = `
            <img src="/pic/profile/${data.iuser}/${data.profile_img}" onerror="this.onerror=null; this.src='/img/defaultProfileImg.jpeg'"></img>
        `;

        profileCtnt.innerHTML = `
            <span>${data.profile_ctnt}</span>
        `;

        profileName.innerHTML = `
            <span>${data.nm}</span>
        `;

}


/* 프로필 history */
const openUp = (iuser) => {
    const popupWidth = 300;
    const popupHeight = 400;
    const popX = 20;
    const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
    const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
        `;

    if(iuser > 0) {
        popup = window.open(`/home/profile/history?ihost=${iuser}`, '', option);
    } else {
        location.href = '/user/login';
    }
}

const historyBtn = document.querySelector('.history-btn');
if (historyBtn) {
    historyBtn.addEventListener('click', () => {
        openUp(iuser);
    });

}

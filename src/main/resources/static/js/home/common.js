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
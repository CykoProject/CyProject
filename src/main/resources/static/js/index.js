const tab_list_css = [
    'tab3','tab4', 'tab5', 'tab6', 'tab7', 'tab8'
]

const tabMenuElem = document.querySelector('.tab-menu');

const url = new URL(window.document.location.href);
const urlParams = url.searchParams;
const iuser = urlParams.get('iuser');
let cnt = 0;

const createAElem = () => {
    return document.createElement('a');
}
const makeProperties = (elem, text, name) => {
    elem.classList.add(tab_list_css[cnt]);
    elem.innerText = text;
    elem.classList.add(name);
    elem.href = `/home/${name}?iuser=1`;
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
    makeProperties(createAElem(), '관리', 'setting');
    cnt = 0;

    const pathName = url.pathname.substr(6);
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
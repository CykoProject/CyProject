const changeUpwForm = document.querySelector('#change_upw');
const changeBtn = document.querySelector('#changeBtn');
//oldUpw
const oldUpwElem = document.querySelector('#oldUpw');
const oldUpwInput = oldUpwElem.querySelector('input');
const oldUpwChk = document.querySelector('#pwChk');

//newUpw
const newUpwElem = document.querySelector('#newUpw');
const newUpwInput = newUpwElem.querySelector('input');

//newUpwChk
const newUpwChkElem = document.querySelector('#newUpwChk');
const newUpwChkInput = newUpwChkElem.querySelector('input')

//Regex
const upwRegex = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;

const errorMsg = (elem, msg) => {
    elem.classList.add('error');
    elem.classList.remove('info');
    elem.querySelector('div').classList.remove('hidden');
    elem.querySelector('div').classList.remove('cinfo');
    elem.querySelector('div').classList.add('cerror');
    elem.querySelector('div').innerText = msg;
}

const infoMsg  = (elem, msg) => {
    elem.classList.add('info');
    elem.classList.remove('error');
    elem.querySelector('div').classList.remove('hidden');
    elem.querySelector('div').classList.remove('cerror');
    elem.querySelector('div').classList.add('cinfo');
    elem.querySelector('div').innerText = msg;
}

let oldUpwTrue = false;
let upwTrue = false;
let upwChkTrue = false;

oldUpwChk.addEventListener('click', (e) => {
    e.preventDefault();
    fetch(`/user/pwChk/${oldUpwInput.value}`)
        .then(res => res.json())
        .then(data => {
            if(data.result === 1){
                infoMsg(oldUpwElem, '비밀번호가 맞습니다.');
                oldUpwTrue = true;
            } else {
                console.log(oldUpwInput.value)
                errorMsg(oldUpwElem, '비밀번호가 틀립니다.');
                oldUpwTrue = false;
            }
        });
});

newUpwInput.addEventListener('keyup',  (e)=> {
    e.preventDefault();
    if(!upwRegex.test(newUpwInput.value)){
        errorMsg(newUpwElem,'숫자,영문 조합 7자리 특문 각 1회 이상');
        upwTrue = false;
    } else {
        infoMsg(newUpwElem, '사용 가능한 비밀번호입니다.');
        upwTrue = true;
    }
});

newUpwChkInput.addEventListener('keyup', (e) => {
    e.preventDefault();
    if(newUpwInput.value !== newUpwChkInput.value){
        errorMsg(newUpwChkElem, '비밀번호가 일치하지 않습니다.');
        upwChkTrue = false;
    } else {
        infoMsg(newUpwChkElem, '비밀번호가 일치합니다.');
        upwChkTrue = true;
    }
});

changeUpwForm.addEventListener('submit', (e) => {
    if(oldUpwTrue === false) {
        alert('현재 비밀번호를 확인해 주세요')
        e.preventDefault();
        return;
    }

    if(upwTrue === false) {
        alert('변경할 비밀번호를 확인해 주세요.');
        e.preventDefault();
        return;
    }

    if(!upwChkTrue || !upwTrue || newUpwInput.value !== newUpwChkInput.value){
        alert('비밀번호가 일치하지 않습니다.')
        e.preventDefault();
        return;
    }
})
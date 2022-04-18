const joinForm = document.querySelector('#joinForm');

//uid
const emailElem = document.querySelector('#email');
const emailInput = emailElem.querySelector('input');
const emailChk = document.querySelector('#idChk');

//upw
const upwElem = document.querySelector('#upw');
const upwInput = upwElem.querySelector('input');

//upwChk
const upwChkElem = document.querySelector('#upwChk');
const upwChkInput = upwChkElem.querySelector('input');

//name
const nameElem = document.querySelector('#name');
const nameInput = nameElem.querySelector('input');

//Regex
const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
const upwRegex = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
const nameRegex = /^([가-힣]{2,5})$/;

const joinBth = document.querySelector('#joinBth')

//join
let emailTrue = false;
let upwTrue = false;
let upwChkTrue = false;
let nameTrue = false;

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

emailChk.addEventListener('click', (e) => {
    e.preventDefault();
    if(!emailRegex.test(emailInput.value)){
        errorMsg(emailElem, '이메일 형식에 맞게 작성해주세요.');
        emailTrue = false;
    } else {
        fetch(`/user/idChk/${emailInput.value}`)
            .then(res => res.json())
            .then(data => {
                if(data.result === 1){
                    errorMsg(emailElem, '이미 등록되어 있는 아이디입니다.');
                    emailTrue = false;
                } else {
                    infoMsg(emailElem, '사용 가능한 아이디입니다.');
                    emailTrue = true;
                }
            });
    }
});

upwInput.addEventListener('keyup',  (e)=> {
    e.preventDefault();
    if(!upwRegex.test(upwInput.value)){
        errorMsg(upwElem,'숫자,영문 조합 7자리 특문 각 1회 이상');
        upwTrue = false;
    } else {
        infoMsg(upwElem,'사용 가능한 비밀번호입니다.');
        upwTrue = true;
    }
});

upwChkInput.addEventListener('keyup', (e) => {
    e.preventDefault();
    if(upwInput.value !== upwChkInput.value){
        errorMsg(upwChkElem, '비밀번호가 일치하지 않습니다.');
        upwChkTrue = false;
    } else {
        infoMsg(upwChkElem, '비밀번호가 일치합니다.');
        upwChkTrue = true;
    }
});

nameInput.addEventListener('keyup', () => {
    if(!nameRegex.test(nameInput.value)){
        errorMsg(nameElem,'이름은 2~5 작성해 주세요.');
        nameTrue = false;
    } else {
        infoMsg(nameElem,'사용 가능한 이름입니다.');
        nameTrue = true;
    }
});


joinForm.addEventListener('submit', (e) => {

    if(emailTrue === false) {
        alert('이메일 중복 확인해 주세요.')
        e.preventDefault();
        return;

    }

    if(upwTrue === false) {
        alert('비밀번호 확인해 주세요.');
        e.preventDefault();
        return;

    }

    if(!upwChkTrue || !upwTrue){
        alert('비밀번호가 일치하지 않습니다.')
        e.preventDefault();
        return;

    }

    if(nameTrue === false){
        alert('이름은 2~5글자 사용해 주세요');
        e.preventDefault();
        return;
    }
});
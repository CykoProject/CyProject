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
const upwChkInput = upwChkElem.querySelector('input')

//name
const nameElem = document.querySelector('#name');
const nameInput = nameElem.querySelector('input');


const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
const upwRegex = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
const nameRegex = /^([가-힣]{2,5})$/;
let email = false;

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
        errorMsg(emailElem, '이메일 형식에 맞게 사용 부탁');
        return false;
    } else {
        fetch(`/user/idChk/${emailInput.value}`)
            .then(res => res.json())
            .then(data => {
                if(data.result === 1){
                    errorMsg(emailElem, '이미 등록되어 있는아이디');
                    return false;
                } else {
                    infoMsg(emailElem, '1단계 통과 다음 관문으로');
                    return true;
                }
            });
    }
});

upwInput.addEventListener('keyup',  (e)=> {
    e.preventDefault();
   if(!upwRegex.test(upwInput.value)){
       errorMsg(upwElem,'숫자,영문 조합 7자리 특문 각 1회 이상');
       return false;
   } else {
       infoMsg(upwElem,'2단계 통과 다음 관문으로');
       return true;
   }
});

upwChkInput.addEventListener('keyup', (e) => {
    e.preventDefault();
    if(upwInput.value !== upwChkInput.value){
        errorMsg(upwChkElem, '비슷하지만 같지않음');
        return false;
    } else {
        infoMsg(upwChkElem, '3단계 통과 다음 관문으로');
        return true;
    }
});

nameInput.addEventListener('keyup', () => {
    if(!nameRegex.test(nameInput.value)){
        errorMsg(nameElem,'두글자 이상 다섯글자 까지 사용 삽가능');
        return false;
    } else {
        infoMsg(nameElem,'4단계 관문 통과 회원가입 과능');
        return true;
    }
});




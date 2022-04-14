const loginFrm = document.querySelector('#loginForm');

const eamil = document.querySelector('#email');
const eamilInput = eamil.querySelector('input');


const upw = document.querySelector('#upw');
const upwInput = upw.querySelector('input');


loginFrm.addEventListener('sumbit', () => {

    fetch(`/user/login`)
})




const phoneRegex = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;

const find_emailElem = document.querySelector('#find_email');
const findInput = find_emailElem.querySelector('input');
const findBth = document.querySelector('#find_email_Bth');



findBth.addEventListener('click', (e)=> {
    e.preventDefault()
    const findEmail = findInput.value;
   if(!phoneRegex.test(findInput.value)){
       alert('휴대폰 번호를 확인해주세요');
   } else {
       fetch(`/user/find_email/${findEmail}`)
           .then(res => res.json())
           .then(data => {
               location.href = `/user/find_email_result?email=${data.resultString}`;
           }).catch(e => {
                console.log(e);
       }) ;
   }
});



const upwPage = document.querySelector('.find_upw_page');
const emailElem = document.querySelector('#email');
const emailInput = emailElem.querySelector('input');
const sendEmail = document.querySelector('#find_upw_Bth');

const errorMsg = (elem, msg) => {
    elem.classList.add('error');
    elem.classList.remove('info');
    elem.querySelector('span').classList.remove('hidden');
    elem.querySelector('span').classList.remove('cinfo');
    elem.querySelector('span').classList.add('cerror');
    elem.querySelector('span').innerText = msg;
}

const infoMsg  = (elem, msg) => {
    elem.classList.add('info');
    elem.classList.remove('error');
    elem.querySelector('span').classList.remove('hidden');
    elem.querySelector('span').classList.remove('cerror');
    elem.querySelector('span').classList.add('cinfo');
    elem.querySelector('span').innerText = msg;
}


const emailCodeElem = (elem) => {
    elem.innerHTML = `
        <h2>비밀번호 찾기</h2>
        <div class="find_upw_input">
            <label>인증번호가 발송 되었습니다.</label>
            <input type="text" class="t_bottom t_bth" id="emailcode" name="emailcode" placeholder="인증번호를 입력해주세요.">
        </div>
        <button id="result_Bth">확인</button>
    `;
}

const upwUpdate = (elem) => {
    elem.innerHTML = `
       <h2>비밀번호 변경</h2>
       <div class="find_upw_input">
            <div id="upw_update" class="t_bottom">
                <label>변경할 비밀번호</label>
                <input type="password" class="t_bottom" name="upw" placeholder="비밀번호">
                <span class="hidden">error</span>
            </div>
            <div id="upw_update_chk" class="t_bottom">
                <label>비밀번호 확인</label>
                <input type="password" class="t_bottom" name="upwChk" placeholder="비밀번호 확인">
                <span class="hidden">error</span>
            </div>
       </div>
       <button id="result_Bth">확인</button>
    `;
}

let emailcode = '';
sendEmail.addEventListener('click', ()=> {
   fetch(`/user/idChk/${emailInput.value}`)
       .then(res => res.json())
       .then(data => {
      if(data.result === 1){
          fetch(`/email`,{
              method: 'post',
              body: JSON.stringify({email: emailInput.value}),
              headers: {'Content-Type': 'application/json'}
          }).then(res => res.json())
              .then(data => {
                  emailCodeElem(upwPage);
                  emailcode = data.resultString;
                  document.querySelector('#result_Bth').addEventListener('click', () => {
                      const code = document.querySelector('#emailcode').value;
                      if(code === emailcode){
                          upwUpdate(upwPage);
                          const upw_update = document.querySelector('#upw_update');
                          const upw_update_input = upw_update.querySelector('input');
                          const upw_update_chk = document.querySelector('#upw_update_chk');
                          const upw_update_chk_input = upw_update_chk.querySelector('input');
                          const upwRegex = /(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/;
                          const upw_update_result = document.querySelector('#result_Bth');
                          let upwTrue = false;
                          let upwChkTrue = false;

                          upw_update_input.addEventListener('keyup', () =>{
                              if(!upwRegex.test(upw_update_input.value)){
                                  errorMsg(upw_update,'숫자,영문 조합 7자리 특문 각 1회 이상');
                                  upwTrue = false;
                              } else {
                                  infoMsg(upw_update,'사용 가능한 비밀번호입니다.');
                                  upwTrue = true;
                              }
                          });
                          upw_update_chk_input.addEventListener('keyup', () =>{
                              if(upw_update_input.value !== upw_update_chk_input.value){
                                  errorMsg(upw_update_chk,'비밀번호가 일치하지 않습니다.');
                                  upwChkTrue = false;
                              } else {
                                  infoMsg(upw_update_chk,'비밀번호가 일치합니다.');
                                  upwChkTrue = true;
                              }
                          });

                          upw_update_result.addEventListener('click', ()=> {
                              if(upw_update_input.value === upw_update_chk_input.value){
                                  fetch(`/user/find_upw`,{
                                      method : 'post',
                                      body : JSON.stringify({
                                          email: emailInput.value,
                                          upw : upw_update_input.value
                                      }),
                                      headers: {'Content-Type':'application/json'}
                                  }).then(res => res.json())
                                      .then(data => {
                                          console.log(data.result);
                                          console.log(emailcode);
                                          alert('변경완료', location.href = `/`);
                                      });
                              } else {
                                  alert('비밀번호를 확인해 주세요.');
                              }
                          })
                      }
                  });
              });
      } else {
          alert('회원 정보가 없습니다. 다시 입력해 주세요.')
      }
   });
});
const findBth = document.querySelector('.friend-find');
const findGo = document.querySelector('.find-go-to');

let popupfind;

const selectFind = (iuser) => {
   const popupWidth = 450;
   const popupHeight = 600;
   const popX = 0;
   const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
   const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
        `;

   if(iuser > 0) {
      popupfind = window.open(`/friendfind`, 'friendfind', option);
   } else {
      location.href = '/user/login';
   }
}
const findGoto = (iuser) => {
   const popupWidth = 450;
   const popupHeight = 600;
   const popX = 0;
   const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
   const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
        `;

   if(iuser > 0) {
      const icategory = 2;
      popupfind = window.open(`/friendfind?category=${icategory}`, 'friendfind', option);
   } else {
      location.href = '/user/login';
   }
}

if(findBth){
   findBth.addEventListener('click',()=>{
      const iuser = parseInt(document.querySelector('#loginUserPk').dataset.iuser);
      selectFind(iuser);
   });
}
if(findGo){
   findGo.addEventListener('click', ()=>{
      const iuser = parseInt(document.querySelector('#loginUserPk').dataset.iuser);
      findGoto(iuser);
   });
}

const fHeaderElem = document.querySelector('#friend-find');
if(fHeaderElem || loginUserElem) {
   const addFriend = document.querySelectorAll('#add-friend');
   const iuser = parseInt(document.querySelector('#loginUserPk').dataset.iuser);

   const setAddFriendCount = (cnt) => {
      const cntElem = document.querySelector('#friend-cnt');
      cntElem.innerText = cnt;
   }

   const fOpen = () => {
      fws.send(`fopen=${iuser}`);
   }

   const fClose = () => {

   }

   const fMsg = (msg) => {
      const data = msg.data;
      const list = data.split('=');
      switch (list[0]) {
         case 'add' :
            setAddFriendCount(list[1]);
            break;
      }
   }

   const fws = new WebSocket("ws://localhost:8090/fs");
   fws.onopen = fOpen;
   fws.onmessage = fMsg;
   fws.onclose = fClose;

   const modalOpen = () => {
      document.querySelector('.modal_wrap').style.display = 'block';
      document.querySelector('.modal_background').style.display = 'block';
   }

   document.querySelector('.modal_background').addEventListener('click', (e) => {
      document.querySelector('.modal_wrap').style.display = 'none';
      document.querySelector('.modal_background').style.display = 'none';
      const nmInput = document.querySelector('#nickname');
      nmInput.value = '';
   });

   if(addFriend.length > 0) {
      addFriend.forEach(item => {
         item.addEventListener('click', (e) => {
            console.log(e.target);
            const asdfBth = document.querySelector('#nickname_bth');
            modalOpen();
            if(asdfBth){
               asdfBth.addEventListener('click', ()=> {
                  const fuser = parseInt(e.target.dataset.fuser);
                  const nmInput = document.querySelector('#nickname');
                  fws.send(`add=${iuser},${fuser},${nmInput.value}`)
                  location.href = '/friendfind?category=2';
               })
            }
            console.log('성ㄱ')
         });
      });
   }

   const accepBtn = document.querySelector('#accept-btn');
   if(accepBtn){
      accepBtn.addEventListener('click', (e)=> {
         const nicknameinputFrm = document.querySelector('#nickname-input-frm');
         const nicknameBtn = document.querySelector('#nickname_bth');
         const fuser = parseInt(e.target.dataset.fuser);
         modalOpen();
         if(nicknameBtn){
            nicknameBtn.addEventListener('click',()=>{
               const nmInput = document.getElementById("nickname");
               const url = `/accept/friend?receiver=${fuser}&nickname=${nmInput.value}`;
               fetch(url)
                   .then(res => res.json())
                   .then(data => {
                      console.log(data)
                      location.href = '/friendfind?category=2';
                   })
                   .catch(e => {

                   });
               console.log(nmInput.value);
            });
         }
      });
   }
}
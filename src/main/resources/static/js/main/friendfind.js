const findBth = document.querySelector('.friend-find');
const findGo = document.querySelector('.find-go-to');

let popupfind;

const selectFind = (iuser) => {
   const popupWidth = 400;
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
   const popupWidth = 400;
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

   if(addFriend.length > 0) {
      addFriend.forEach(item => {
         item.addEventListener('click', () => {
            const fuser = parseInt(document.querySelector('#fuser').dataset.fuser);
            const nmInput = document.querySelector('#nickname');
            fws.send(`add=${iuser},${fuser},${nmInput.value}`);
            console.log('성ㄱ')
         });
      });
   }
}
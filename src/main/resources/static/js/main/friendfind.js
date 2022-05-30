const findBth = document.querySelector('.friend-find');
let popupfind;

const selectFind = (iuser) => {
   const popupWidth = 1189;
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

if(findBth){
   findBth.addEventListener('click',()=>{
      const iuser = parseInt(document.querySelector('#loginUserPk').dataset.iuser);
      selectFind(iuser);
   });
}

const fHeaderElem = document.querySelector('#friend-find');
if(fHeaderElem || loginUserElem) {
   const addFriend = document.querySelector('#add-friend');
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

   if(addFriend) {
      addFriend.addEventListener('click', () => {
         const fuser = parseInt(document.querySelector('#fuser').dataset.fuser);
         fws.send(`add=${iuser},${fuser}`);
      });
   }
}
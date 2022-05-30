

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
      popupfind = window.open(`/friendfind?iuser=${iuser}`, 'friendfind', option);
   } else {
      location.href = '/user/login';
   }
}

if(findBth){
   findBth.addEventListener('click',()=>{
      selectFind(findBth.dataset.iuser);
   });
}
let paginationElem = document.querySelector(".pagination");

let resultsCount = Number(document.querySelector(".count-number").textContent);

let currentUrl = window.location.href;
let urlSplit = currentUrl.split('&', 2);
currentUrl = urlSplit[0] +'&'+ urlSplit[1];
console.log(currentUrl);

function goToNumber(i) {
    location.href = currentUrl + '&page=' + i;
}
const makePagingNumber = () => {
    for (let i = 1; i <= Math.ceil(resultsCount/5); i++) {
        paginationElem.innerHTML += `
        <span class="number" onclick="goToNumber(${i-1})">${i}</span>
        `;
    }
    document.querySelectorAll(".number")[0].style.fontWeight= "bold";
    document.querySelectorAll(".number")[0].style.fontSize = "large";
    if(new URL(location.href).searchParams.get("page")) {
        document.querySelectorAll(".number")[0].style.fontWeight= "";
        document.querySelectorAll(".number")[0].style.fontSize = "";
        let i = new URL(location.href).searchParams.get("page");
        document.querySelectorAll(".number")[i].style.fontWeight= "bold";
        document.querySelectorAll(".number")[i].style.fontSize = "large";
    }
}
makePagingNumber();

//파라미터 받아와서 입력한 검색어, 타입 그대로 유지하기

let searchTxt = decodeURI(decodeURIComponent(location.search.split("&")[1].split("=")[1]));
let searchType = location.search.split("&")[0].slice(1).split("=")[1];

let searchTxtElem = document.querySelector(".search-text");
let searchTypeElem = document.querySelector(".search-conditions");

searchTxtElem.value = searchTxt;
searchTypeElem.value = searchType;

console.log(searchTxt);
console.log(searchType);

let userImgElem = document.querySelectorAll(".user-img > img");

userImgElem.forEach((item)=> {
    let userId = item.parentNode.parentNode.querySelector(".userId").textContent;
    console.log(userId)
    item.addEventListener("click", ()=> {
        const openUp = (userId) => {
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

            if(userId > 0) {
                popup = window.open(`/home?iuser=${userId}`, 'home', option);
            } else {
                location.href = '/user/login';
            }
        }
        openUp(userId);
    })
})
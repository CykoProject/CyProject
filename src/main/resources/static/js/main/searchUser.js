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
        <span onclick="goToNumber(${i-1})">${i}</span>
        `;
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
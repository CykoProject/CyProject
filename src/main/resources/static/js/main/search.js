let searchTypeValue = document.querySelector(".search-conditions > option").value;




let searchAllBtn = document.querySelector(".see-all");
let searchProfileBtn = document.querySelector(".see-profile");
let searchDiaryBtn = document.querySelector(".see-diary");
let searchPhotoBtn = document.querySelector(".see-photo");


let searchCount = document.querySelector(".count-number");

let photoElem = document.querySelector(".photo-elem");
let diaryElem = document.querySelector(".diary-elem");
let profileElem = document.querySelector(".profile-elem");

let allList = document.querySelectorAll(".results-data");

const changeElemBlock = (elem) => {
    for(let i=0; i<elem.children.length; i++) {
        elem.children[i].style.display = 'flex';
    }
}

searchProfileBtn.addEventListener("click", () => {
    searchCount.textContent = document.querySelectorAll(".results-profile").length;

    profileElem.style.display = "block";
    changeElemBlock(profileElem);
    photoElem.style.display = "none";
    diaryElem.style.display = "none";
})

searchPhotoBtn.addEventListener("click", () => {
    searchCount.textContent = document.querySelectorAll(".results-photo").length;
    changeElemBlock(photoElem);
    photoElem.style.display = "block";
    profileElem.style.display = "none";
    diaryElem.style.display = "none";
})

searchDiaryBtn.addEventListener("click", () => {
    searchCount.textContent = document.querySelectorAll(".results-diary").length;
    changeElemBlock(diaryElem);
    diaryElem.style.display = "block";
    photoElem.style.display = "none";
    profileElem.style.display = "none";
})

searchAllBtn.addEventListener("click", () => {
    location.reload();
    searchCount.textContent = document.querySelectorAll(".results-photo").length + document.querySelectorAll(".results-diary").length + document.querySelectorAll(".results-profile").length;

    diaryElem.style.display = "block";
    photoElem.style.display = "block";
    profileElem.style.display = "block";
})

let cnt = 10;
const showResult10 = () => {
    for (let i = 0; i <allList.length; i++) {
        if(i<cnt) {
            allList[i].style.display = "flex";
        } else {
            allList[i].style.display = "none";
        }
    }
    cnt += 10;
}
showResult10();

//인피니티 스크롤
window.onscroll = function(e) {
    if(Math.ceil(window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
        showResult10();
    }
}

//파라미터 받아와서 입력한 검색어, 타입 그대로 유지하기

let searchTxt = decodeURI(decodeURIComponent(location.search.split("&")[1].split("=")[1]));
let searchType = location.search.split("&")[0].slice(1).split("=")[1];

let searchTxtElem = document.querySelector(".search-text");
let searchTypeElem = document.querySelector(".search-conditions");

searchTxtElem.value = searchTxt;
searchTypeElem.value = searchType;

console.log(searchTxt);
console.log(searchType);
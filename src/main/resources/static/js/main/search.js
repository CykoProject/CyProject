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
        if (i>10) {
            elem.children[i].style.display = 'none';
        }
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

const photos = document.querySelectorAll('.photo-img');
if(photos.length > 0) {
    photos.forEach(item => {
        const iphoto = parseInt(item.dataset.iphoto);
        fetch(`/ajax/home/photo?iphoto=${iphoto}`)
            .then(res => res.json())
            .then(data => {
                data.forEach(item2 => {
                    const img = item.querySelector('.photo-img-each');
                    img.src = `/pic/photos/${iphoto}/${item2.img}`;
                });
            })
            .catch(e => {
                console.error(e);
            });
    });
}

//게시물 사진 없을 때 기본 사진
let photoImgElem = document.querySelectorAll(".photo-img > img");
let diaryImgElem = document.querySelectorAll(".diary-img > img");
let profileImgElem = document.querySelectorAll(".profile-img > img");

photoImgElem.forEach((item)=> {
    if(item.getAttribute("src") == null || undefined) {
        item.src = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT01d4OBfsfcn3i82usjadKKTPbAtKGIkgRcA&usqp=CAU"
    }
    let userId = item.parentNode.parentNode.querySelector(".userId").textContent;
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
                popup = window.open(`/home/photo?iuser=${userId}`, 'home', option);
            } else {
                location.href = '/user/login';
            }
        }
        openUp(userId);
    })
})

diaryImgElem.forEach((item)=> {
    if(item.getAttribute("src") == ""||null || 0) {
        item.src = "https://entertainimg.kbsmedia.co.kr/cms/uploads/PERSON_20220112081105_4217f0cc8e5e82a908647d8e1de448a5.jpg"
    }
    let userId = item.parentNode.parentNode.querySelector(".userId").textContent;
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
                popup = window.open(`/home/diary?iuser=${userId}`, 'home', option);
            } else {
                location.href = '/user/login';
            }
        }
        openUp(userId);
    })
})

profileImgElem.forEach((item)=> {
    if(item.getAttribute("src") == null || 0) {
        item.src = "https://cdn.mhnse.com/news/photo/202205/105045_88610_1253.jpg"
    }
    let userId = item.parentNode.parentNode.querySelector(".userId").textContent;
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
                popup = window.open(`/home/profile?iuser=${userId}`, 'home', option);
            } else {
                location.href = '/user/login';
            }
        }
        openUp(userId);
    })
})


//미니홈피 바로가기
const goHome = document.querySelector('.profile-go-to-home');
if(goHome) {
    goHome.addEventListener('click', () => {
        const popupWidth = 1189;
        const popupHeight = 600;
        const popX = (window.screen.width / 2) - (popupWidth / 2);
        const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
        const iuser = goHome.dataset.iuser;
        const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
        `;

        if(iuser > 0) {
            window.open(`/home?iuser=${iuser}`, 'home', option);
        } else {
            location.href = '/user/login';
        }
    });
}

// WebSocket with Stomp ==================================
const loginUserPk = parseInt(document.querySelector('#loginUserPk').dataset.iuser);
if(loginUserPk > 0) {

    const makeCnt = (iuser, cnt) => {
        const onlineCnt = document.querySelector('#online-friends-cnt');
        if(loginUserPk === iuser) {
            onlineCnt.innerText = cnt;
        }
    }

    const makeMsgCnt = (data) => {
        const msgNoticeElem = document.querySelector('#msg-notice');
        if(msgNoticeElem) {
            const keys = Object.keys(data);
            keys.forEach(item => {
                console.log('keys : ' + item);
                if(loginUserPk === parseInt(item)) {
                    msgNoticeElem.innerText = data[item];
                }
            });
        }
    }
    const ws = new WebSocket("ws://localhost:8090/ws");
    ws.onopen = onOpen;
    ws.onclose = onClose;
    ws.onmessage = onMessage;

    function onOpen(evt) {
        var str = "open="+loginUserPk;
        ws.send(str);
    }

    function onClose(evt) {
        var str = "logout="+loginUserPk;
        ws.send(str);
    }

    function onMessage(msg) {
        const data = JSON.parse(msg.data);
        const status = Object.keys(data);
        if(status.includes('msgCnt')) {
            makeMsgCnt(data['msgCnt']);
        } else {
            for (let i in data) {
                const iuser = parseInt(i);
                const cnt = data[i];
                makeCnt(iuser, cnt);
            }
        }
    }
    const msgBtn = document.querySelector('.msg-submit-btn');
    if(msgBtn) {
        msgBtn.addEventListener('click', () => {
            const ctnt = document.querySelector('.cus-textarea').value;
            const chkArr = document.querySelectorAll('.friends-chk');
            const receiverArr = [];
            chkArr.forEach(item => {
                if(item.checked) {
                    receiverArr.push(item.closest('.friends-data').dataset.receiver);
                }
            });
            ws.send(`msg={"receiver" : "${receiverArr}", "iuser":${loginUserPk}, "ctnt":"${ctnt}"}`);
        });
    }
}
//게시글, 친구 검색


const headerSearchBtn = document.querySelector(".search-btn");

headerSearchBtn.addEventListener("click", () => {
    let headerSelectVal = document.querySelector(".search-conditions").value;
    let headerSearchVal = document.querySelector(".search-text").value;

    console.log(headerSelectVal);
    console.log(headerSearchVal);
    fetch("http://localhost:8090/friendSearch", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "search": headerSearchVal
        }),
    }).then(res => {
        console.log(res)
        return res.json();
    }).catch(e => console.log(e.message));
})


let newsElem = document.querySelector(".main-news");
// ../../../java/com/example/CyProject/main/MainNewsApiController
fetch("http://localhost:8090/api/news")
    .then(res => res.json())
    .then(data => {
        console.log(data.items);
        for (let i = 0; i <= data.items.length; i++) {
            newsElem.innerHTML += `
<div style="margin-bottom: 5px; display: inline-block;"><a href="${data.items[i].link}" style = "text-decoration: none; color: black;" target='_blank'>${data.items[i].title}</a></div>
`
        }
    }).catch(e => {
    console.log(e.message);
})

//뉴스 검색

const newsSearchBtn = document.querySelector(".news-search-button");
let searchTxt = document.querySelector(".news-search");

newsSearchBtn.addEventListener("click", () => {
    console.log(searchTxt.value)
    newsSearch(searchTxt.value);
})


function newsSearch(searchTxt) {
    fetch("http://localhost:8090/api/news", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            "searchTxt": searchTxt
        }),
    }).then(async res => {
        await getNews(searchTxt)
        console.log(res)
        return res.json();
    }).catch(e => console.log(e.message));
}


function getNews(searchTxt) {
    fetch("http://localhost:8090/api/news/search?searchTxt="+`${searchTxt}`)
        .then(res => res.json())
        .then(data => {
            console.log(data.items);
            newsElem.innerHTML = null;
            for (let i = 0; i <= data.items.length; i++) {
                newsElem.innerHTML += `
<div style="margin-bottom: 5px; display: inline-block;"><a href="${data.items[i].link}" style = "text-decoration: none; color: black;" target='_blank'>${data.items[i].title}</a></div>
`
            }
        }).catch(e => {
        console.log(e.message);
    });
}

//웹툰 api

const webtoonElem = document.querySelector(".webtoon-list");

function getWebtoon() {
    fetch("http://localhost:8090/api/webtoon")
        .then(res => res.json())
        .then(data => {
            console.log(data);
            webtoonElem.innerHTML = null;
            for (let i = 0; i <= data.length; i++) {
                const title = data[i].title;
                const image = data[i].image;
                const link = data[i].link;
                const writer = data[i].writer;
                const userRating = data[i].userRating;
                webtoonElem.innerHTML += `
                <div class="eachWebtoon" style="display: flex; flex-direction: column; text-align: center; padding: 5px; width: 100px; height: 120px; border: solid 1px #b2b2b2; padding: 5px;">
                <a href="${link}" style = "text-decoration: none; color: black;" target='_blank'>
                <img src="${image}" style="width:100px; height:120px;" >
                <p style="margin: 5px; font-weight: bold;">${title}</p>
                <p style="margin: 5px; font-size: small;">작가 : ${writer}</p>
                <p style="margin: 5px; font-size: small;">평점 : ${userRating}</p>
                </a>
</div>
`;

                const eachWebtoonElem = document.querySelectorAll(".eachWebtoon");

                let webtoonVisible = [];
                let webtoonInvisible =[];

                for (let i = 0; i < eachWebtoonElem.length; i++) {
                    if (i < 5) {
                        eachWebtoonElem[i].style = "display: flex";
                        webtoonVisible.push(eachWebtoonElem[i]);
                    } else {
                        eachWebtoonElem[i].style = "display: none";
                        webtoonInvisible.push(eachWebtoonElem[i]);
                    }
                }
                // console.log("init : " + webtoonVisible)
                // console.log("init : " + webtoonInvisible)

                const prevBtn = document.querySelector(".webtoon-prev-btn");
                const nextBtn = document.querySelector(".webtoon-next-btn");

                prevBtn.addEventListener("click", () => {
                    for (let i = 0; i < eachWebtoonElem.length; i++) {
                        if (eachWebtoonElem[i].style = "display: flex") {
                            eachWebtoonElem[i].style = "display: none";
                            webtoonInvisible.push(eachWebtoonElem[i]);
                            webtoonVisible.splice(0, 5)
                        }
                    }
                    // console.log("prev : " + webtoonVisible)
                    // console.log("prev : " + webtoonInvisible)
                    for (let i=10; i < 15; i++) {
                        webtoonInvisible[i].style = "display: flex"
                        webtoonVisible.push(webtoonInvisible[i]);
                    }
                    webtoonInvisible.splice(10,5)
                    // console.log("prev : " + webtoonVisible)
                    // console.log("prev : " + webtoonInvisible)

                })

                nextBtn.addEventListener("click", () => {

                    for (let i = 0; i < eachWebtoonElem.length; i++) {
                        if (eachWebtoonElem[i].style = "display: flex") {
                            eachWebtoonElem[i].style = "display: none";
                            webtoonVisible.splice(0,5)
                            webtoonInvisible.push(eachWebtoonElem[i]);
                        }
                    }
                    // console.log("next : " + webtoonVisible)
                    // console.log("next : " + webtoonInvisible)
                    for (let i=0; i < 5; i++) {
                        webtoonInvisible[i].style = "display: flex"
                        webtoonVisible.push(webtoonInvisible[i]);
                    }
                    webtoonInvisible.splice(0,5)
                    // console.log("next : " + webtoonVisible)
                    // console.log("next : " + webtoonInvisible)
                })
            }
        }).catch(e => {
        console.log(e.message);
    })
}

getWebtoon();

//영화진흥위원회 api

function getYesterday(){
    var loadDt = new Date();
    var day1 = new Date(Date.parse(loadDt) - 1 * 1000 * 60 * 60 * 24); //하루전

    var year = day1.getFullYear();
    var month = ("0" + (1 + day1.getMonth())).slice(-2);
    var day = ("0" + day1.getDate()).slice(-2);

    return year + month + day;
}

let yesterday = getYesterday();
console.log(yesterday)

const movieElem = document.querySelector(".main-movie");
const movieListElem = document.querySelector(".movie-list");


fetch("http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=446ae4859b661a930105e6293785fbfc&targetDt=" + yesterday)
    .then(res => res.json())
    .then(data => {
        let movieList = [];
        console.log(data.boxOfficeResult.dailyBoxOfficeList);
        for (let i = 0; i < data.boxOfficeResult.dailyBoxOfficeList.length; i++) {
            movieList.push(data.boxOfficeResult.dailyBoxOfficeList[i].movieNm)
        }
        console.log(movieList);

        fetch("http://localhost:8090/api/movie", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "title1": movieList[0],
                "title2": movieList[1],
                "title3": movieList[2],
                "title4": movieList[3],
                "title5": movieList[4],
                "title6": movieList[5],
                "title7": movieList[6],
                "title8": movieList[7],
                "title9": movieList[8],
                "title10": movieList[9]
            }),
        }).then(async res => {
            await getMovieInfo();
            return res.json();
        })
            .then(data => console.log(data))
            .catch(e => console.log(e.message));
    })

    .catch(e => {
        console.error(e);
    });


function getMovieInfo() {
    fetch("http://localhost:8090/api/movie")
        .then(res => res.json())
        .then(data => {
            console.log(data)
            for (let i = 0; i < data.length; i++) {
                const title = data[i].title;
                const image = data[i].image;
                const link = data[i].link;
                const userRating = data[i].userRating;
                movieListElem.innerHTML += `
                <div class="eachMovie" style="display: flex; flex-direction: column; text-align: center; padding: 5px; width: 200px; height: 287px; border: solid 1px #b2b2b2; padding: 5px;">
                <a href="${link}" style = "text-decoration: none; color: black;" target='_blank'>
                <img src="${image}" style="width:200px; height:287px;" >
                <p style="margin: 5px; font-weight: bold;">${title}</p>
                <p style="margin: 5px; font-size: small;">평점 : ${userRating}</p>
                </a>
</div>
`;
            }

            const eachMovieElem = document.querySelectorAll(".eachMovie");

            for (let i = 0; i < eachMovieElem.length; i++) {
                if (i >= 5) {
                    eachMovieElem[i].style = "display: none";
                }
            }

            const prevBtn = document.querySelector(".movie-prev-btn");
            const nextBtn = document.querySelector(".movie-next-btn");

            prevBtn.addEventListener("click", () => {
                for (let i = 0; i < eachMovieElem.length; i++) {
                    eachMovieElem[i].style = "display: flex";
                    if (i >= 5) {
                        eachMovieElem[i].style = "display: none";
                    }
                }
            })

            nextBtn.addEventListener("click", () => {
                for (let i = 0; i < eachMovieElem.length; i++) {
                    eachMovieElem[i].style = "display: flex";
                    if (i < 5) {
                        eachMovieElem[i].style = "display: none";
                    }
                }
            })
        })
        .catch(e => e.message)
}


//네이버 영화  검색 api

// fetch("http://localhost:8090/api/movie")
//     .then(res => res.json())
//     .then(data => {
//         console.log(data);
//     }).catch(e => {
//     console.log(e.message);
// })

//지도 api
const mapContainer = document.querySelector('#map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(35.866159, 128.593817), // 지도의 중심좌표
        level: 2, // 지도의 확대 레벨
        mapTypeId: kakao.maps.MapTypeId.ROADMAP // 지도종류
    };
// 지도를 생성한다
const map = new kakao.maps.Map(mapContainer, mapOption);
// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(35.866159, 128.593817), // 마커의 좌표
    map: map // 마커를 표시할 지도 객체
});
const infowindow = new kakao.maps.InfoWindow({
    content: '<div style="padding:5px;">처음 시작된 곳</div>' // 인포윈도우에 표시할 내용
});
// 인포윈도우를 지도에 표시한다
infowindow.open(map, marker)


//회원가입 페이지 이동
const join_section = document.querySelector('.join-section');

join_section.addEventListener('click' , () => {
    location.href = `/user/join`
});

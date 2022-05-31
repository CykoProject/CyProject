//미니홈피 바로가기
const goHome = document.querySelector('.profile-go-to-home');
const goFriendHome = document.querySelectorAll('.profile-go-to-friend-home');
const goVisit = document.querySelector('.visit-go-to');
let popup;

const openUp = (iuser) => {
    const popupWidth = 1189;
    const popupHeight = 600;
    const popX = (window.screen.width / 2) - (popupWidth / 2);
    const popY = (window.screen.height / 2) - (popupHeight / 2) - 100;
    const option = `width = ${popupWidth}px
        , height = ${popupHeight}px
        , left = ${popX}
        , top = ${popY}
        , scrollbars = no
        `;

    if (iuser > 0) {
        popup = window.open(`/home?iuser=${iuser}`, 'home', option);
    } else {
        location.href = '/user/login';
    }
}
const openVisit = (iuser) => {
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

    if (iuser > 0) {
        popup = window.open(`/home/visit?iuser=${iuser}`, 'home', option);
    } else {
        location.href = '/user/login';
    }
}

if (goHome) {
    goHome.addEventListener('click', () => {
        openUp(goHome.dataset.iuser);
    });
}
if (goVisit) {
    goVisit.addEventListener('click', () => {
        openVisit(goVisit.dataset.iuser);
    })
}


if (goFriendHome.length > 0) {
    goFriendHome.forEach(item => {
        item.addEventListener('click', (e) => {
            openUp(e.target.dataset.iuser);
        });
    });
}

// WebSocket with Stomp ==================================
const loginUserElem = document.querySelector('#loginUserPk');
if (loginUserElem) {
    const loginUserPk = parseInt(loginUserElem.dataset.iuser);
    if (loginUserPk > 0) {

        const makeCnt = (iuser, cnt, logout) => {
            const onlineCnt = document.querySelector('#online-friends-cnt');
            if (onlineCnt) {
                if (logout != null && logout != undefined && logout != '') {
                    if (loginUserPk === parseInt(iuser)) {
                        const preCnt = parseInt(onlineCnt.innerText);
                        const result = preCnt + cnt;
                        onlineCnt.innerText = result;
                    }
                } else {
                    if (loginUserPk === iuser) {
                        onlineCnt.innerText = cnt;
                    }
                }
            }
        }

        const makeMsgCnt = (data) => {
            const msgNoticeElem = document.querySelector('#msg-notice');
            if (msgNoticeElem) {
                const keys = Object.keys(data);
                keys.forEach(item => {
                    console.log('keys : ' + item);
                    if (loginUserPk === parseInt(item)) {
                        msgNoticeElem.innerText = data[item];
                    }
                });
            }
        }

        const msgAlarm = () => {
            const divElem = document.createElement('div');
            divElem.classList.add('msg-alarm');
            divElem.innerHTML = `
                <span>쪽지가 도착했습니다 !</span>
            `;
            const header = document.querySelector('.header');
            divElem.addEventListener('click', () => {
                location.href = '/msg/inbox';
            });
            header.appendChild(divElem);

            let setTimeOut = setTimeout(() => {
                divElem.remove();
            }, 3000);

            divElem.addEventListener('mouseover', () => {
                clearTimeout(setTimeOut);
            });

            divElem.addEventListener('mouseout', () => {
                setTimeOut = setTimeout(() => {
                    divElem.remove();
                }, 3000);
            });
        }

        const ws = new WebSocket("ws://localhost:8090/ws");
        ws.onopen = onOpen;
        ws.onclose = onClose;
        ws.onmessage = onMessage;

        const logoutBtnElem = document.querySelector('.logout-btn');
        if (logoutBtnElem) {
            logoutBtnElem.addEventListener('click', () => {
                onClose();
            });
        }

        function onOpen(evt) {
            var str = "open=" + loginUserPk;
            ws.send(str);
        }

        function onClose(evt) {
            var str = "logout=" + loginUserPk;
            ws.send(str);
        }

        function onMessage(msg) {
            const data = JSON.parse(msg.data);
            const status = Object.keys(data);
            if (status.includes('msgCnt')) {
                makeMsgCnt(data['msgCnt']);
                msgAlarm();
            } else if (status.includes('logout')) {
                const logoutData = data['logout'];
                for (let i in logoutData) {
                    const iuser = parseInt(i);
                    console.log(iuser);
                    const cnt = logoutData[i];
                    makeCnt(iuser, cnt, 'logout');
                }
            } else {
                for (let i in data) {
                    const iuser = parseInt(i);
                    const cnt = data[i];
                    makeCnt(iuser, cnt);
                }
            }
        }

        const msgBtn = document.querySelector('.msg-submit-btn');
        if (msgBtn) {
            msgBtn.addEventListener('click', () => {
                const ctnt = document.querySelector('.cus-textarea').value;
                const chkArr = document.querySelectorAll('.friends-chk');
                const receiverArr = [];
                chkArr.forEach(item => {
                    if (item.checked) {
                        receiverArr.push(item.closest('.friends-data').dataset.receiver);
                    }
                });
                ws.send(`msg={"receiver" : "${receiverArr}", "iuser":${loginUserPk}, "ctnt":"${ctnt}"}`);
            });
        }
    }
}


//게시글, 친구 검색
const mainContainerElem = document.querySelector('.container');
if (mainContainerElem) {
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
        fetch("http://localhost:8090/api/news/search?searchTxt=" + `${searchTxt}`)
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
                <div class="eachWebtoon" style="display: flex; flex-direction: column; text-align: center; padding: 5px; max-width: 100px; height: 120px; border: solid 1px #b2b2b2; padding: 5px;">
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
                    let webtoonInvisible = [];

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
                        for (let i = 10; i < 15; i++) {
                            webtoonInvisible[i].style = "display: flex"
                            webtoonVisible.push(webtoonInvisible[i]);
                        }
                        webtoonInvisible.splice(10, 5)
                        // console.log("prev : " + webtoonVisible)
                        // console.log("prev : " + webtoonInvisible)

                    })

                    nextBtn.addEventListener("click", () => {

                        for (let i = 0; i < eachWebtoonElem.length; i++) {
                            if (eachWebtoonElem[i].style = "display: flex") {
                                eachWebtoonElem[i].style = "display: none";
                                webtoonVisible.splice(0, 5)
                                webtoonInvisible.push(eachWebtoonElem[i]);
                            }
                        }
                        // console.log("next : " + webtoonVisible)
                        // console.log("next : " + webtoonInvisible)
                        for (let i = 0; i < 5; i++) {
                            webtoonInvisible[i].style = "display: flex"
                            webtoonVisible.push(webtoonInvisible[i]);
                        }
                        webtoonInvisible.splice(0, 5)
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

    function getYesterday() {
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
                <div class="eachMovie" style="display: flex; flex-direction: column; text-align: center; padding: 5px; width: 120px; height: 150px;">
                <a href="${link}" style = "text-decoration: none; color: black;" target='_blank'>
                <img src="${image}" style="width:120px; height:170px;" >
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

//한 마디
    let mainCmtBtn = document.querySelector(".main-cmt-button");
    let mainCmtElem = document.querySelector(".main-cmt-list");
    let mainCmtPagingElem = document.querySelector(".pagination");
    let pagingNum;
    let pagingDefaultNum = 5;
    let pagingCurrentNum = 1;
    let pagingUnit = 1;

    let maxPage = pagingUnit * pagingDefaultNum;
    let minPage = ((pagingUnit - 1) * pagingDefaultNum) + 1;

//한 마디 페이지 정보 불러오기
    function callPageInfo(pagingNum) {
        fetch(`/cmt?page=${pagingNum}`)
            .then(res => res.json())
            .then(data => {
                mainCmtElem.innerHTML = ``;
                data.forEach((item) => {
                    mainCmtElem.innerHTML += `<div class="main-cmt-each">
                            <span>${item.ctnt}</span><span>${item.iuser.nm}</span>
                            </div>
                            `
                })
            })
    }

    function pagingCount() {
        fetch("/cmt/count")
            .then(res => res.json())
            .then(data => {
                pagingNum = Math.ceil(data / 10); // 11
                mainCmtPagingElem.innerHTML = '';
                if (pagingNum > pagingDefaultNum) {
                    mainCmtPagingElem.innerHTML += `
                        <p class="prev"><</p>
                        `;

                    for (let i = pagingCurrentNum; i <= (maxPage <= pagingNum ? maxPage : pagingNum); i++) {
                        mainCmtPagingElem.innerHTML += `
                        <p class="number">${i}</p>
                        `
                    }
                    mainCmtPagingElem.innerHTML += `
                        <p class="next">></p>
                        `
                    let prevBtn = document.querySelector(".prev");
                    let nextBtn = document.querySelector(".next");

                    if (pagingCurrentNum > 5) {
                        prevBtn.addEventListener("click", () => {
                            nextBtn.style.pointerEvents = "auto";
                            pagingUnit--;
                            maxPage = pagingUnit * pagingDefaultNum;
                            minPage = ((pagingUnit - 1) * pagingDefaultNum) + 1;
                            pagingCurrentNum = minPage;
                            console.log(pagingCurrentNum);
                            pagingCount();
                            callPageInfo(minPage-1);
                        })
                    } else {
                        prevBtn.style.pointerEvents = "none";
                    }

                    if (maxPage < pagingNum) {
                        nextBtn.addEventListener("click", () => {
                            prevBtn.style.pointerEvents = "auto";
                            pagingUnit++;
                            maxPage = pagingUnit * pagingDefaultNum;
                            minPage = ((pagingUnit - 1) * pagingDefaultNum) + 1;
                            pagingCurrentNum = minPage;
                            console.log(pagingCurrentNum);
                            pagingCount();
                            callPageInfo(minPage-1);
                        })
                    } else {
                        nextBtn.style.pointerEvents = "none";
                    }


                } else {
                    for (let i = 1; i <= pagingNum; i++) {
                        mainCmtPagingElem.innerHTML += `
                        <p class="number">${i}</p>
                        `
                    }
                }
                let pagingNumElem = document.querySelectorAll(".number")
                pagingNumElem[0].style.fontSize = "18px";
                pagingNumElem[0].style.fontWeight = "bold";

                pagingNumElem.forEach((item) => {
                    item.addEventListener("click", () => {
                        pagingNumElem.forEach((item) => {
                            item.style = '';
                        })
                        let pagingNum = parseInt(item.textContent) - 1;
                        console.log(pagingNum);

                        callPageInfo(pagingNum);
                        item.style.fontSize = "18px";
                        item.style.fontWeight = "bold";
                    })
                })
            })

    }

    pagingCount();

    mainCmtBtn.addEventListener('click', () => {
        let ctnt = document.querySelector(".main-cmt-ctnt").value;
        if (!loginUserElem) {
            alert("로그인 후 서비스를 이용할 수 있어요")
            return;
        }
        if (ctnt.length > 20) {
            alert("20자 이내로 작성해 주세요")
            return;
        }
        fetch("/cmt/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: ctnt,
        })
            .then(res => res.json())
            .then(data => {
                console.log(data)
                fetch("/cmt")
                    .then(res => res.json())
                    .then(data => {
                        console.log(data)
                        mainCmtElem.innerHTML = ``;
                        data.forEach((item) => {
                            mainCmtElem.innerHTML += `<div class="main-cmt-each">
                            <span>${item.ctnt}</span><span>${item.iuser.nm}</span>
                            </div>
                            `
                        })
                        fetch("/cmt/count")
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                pagingNum = Math.ceil(data / 10);
                                console.log(pagingNum);
                                mainCmtPagingElem.innerHTML = '';
                                if (pagingNum > pagingDefaultNum) {
                                    mainCmtPagingElem.innerHTML += `
                        <p class="prev"><</p>
                        `
                                    for (let i = 1; i <= pagingNum; i++) {
                                        mainCmtPagingElem.innerHTML += `
                        <p class="number">${i}</p>
                        `
                                    }
                                    mainCmtPagingElem.innerHTML += `
                        <p class="next">></p>
                        `
                                } else {
                                    for (let i = 1; i <= pagingNum; i++) {
                                        mainCmtPagingElem.innerHTML += `
                        <p class="number">${i}</p>
                        `
                                    }
                                }
                                let pagingNumElem = document.querySelectorAll(".number")

                                pagingNumElem[0].style.fontSize = "18px";
                                pagingNumElem[0].style.fontWeight = "bold";

                                pagingNumElem.forEach((item) => {
                                    item.addEventListener("click", () => {
                                        pagingNumElem.forEach((item) => {
                                            item.style = '';
                                        })
                                        let pagingNum = parseInt(item.textContent) - 1;
                                        console.log(pagingNum);
                                        fetch(`/cmt?page=${pagingNum}`)
                                            .then(res => res.json())
                                            .then(data => {
                                                mainCmtElem.innerHTML = ``;
                                                data.forEach((item) => {
                                                    mainCmtElem.innerHTML += `<div class="main-cmt-each">
                            <span>${item.ctnt}</span><span>${item.iuser.nm}</span>
                            </div>
                            `
                                                })
                                                item.style.fontSize = "18px";
                                                item.style.fontWeight = "bold";
                                            })
                                    })
                                })
                            })
                        document.querySelector(".main-cmt-ctnt").value = '';
                    })
            })
            .catch(e => console.error(e))
    })

    let pagingNumElem = document.querySelectorAll(".number")

    pagingNumElem.forEach((item) => {
        item.addEventListener("click", () => {
            let pagingNum = parseInt(item.textContent) - 1;
            console.log(pagingNum);
            fetch(`/cmt?page=${pagingNum}`)
                .then(res => res.json())
                .then(data => {
                    mainCmtElem.innerHTML = ``;
                    data.forEach((item) => {
                        mainCmtElem.innerHTML += `<div class="main-cmt-each">
                            <span>${item.ctnt}</span><span>${item.iuser.nm}</span>
                            </div>
                            `
                    })
                })
        })
    })

//회원가입 페이지 이동
    const
        join_section = document.querySelector('.join-section');
    if (join_section) {
        join_section.addEventListener('click', () => {
            location.href = `/user/join`
        });
    }
    //아이디 저장
    const login_save_Bth = document.querySelector('#login_btn');
    const frm = document.querySelector('#loginFrm');
    const loginSave = frm.querySelector('#saveIdChk');


    const setCookie = (cookie_name, value, days) => {
        let exdate = new Date();
        exdate.setDate(exdate.getDate() + days);
        const cookieValue = escape(value) + ((days == null) ? '' : '; expires=' + exdate.toUTCString());
        document.cookie = cookie_name + '=' + cookieValue;
    }

    const getCookie = cookie_name => {
        let x, y;
        const val = document.cookie.split(';');

        for (let i = 0; i < val.length; i++) {
            x = val[i].substr(0, val[i].indexOf('='));
            y = val[i].substr(val[i].indexOf('=') + 1);
            x = x.replace(/^\s+|\s+$/g, '');
            if (x === cookie_name) {
                return unescape(y);
            }
        }
    }

    const chk = () => {
        if (loginSave.checked) {
            setCookie('c_userid', frm.email.value, '100');
        } else {
            setCookie('c_userid', '', '100');
        }
    }

    login_save_Bth.addEventListener('click', () => {
        chk();
    });

    let id = getCookie('c_userid');
    if (id === null || typeof id === 'undefined' || id === '') {
        id = '';
    } else {
        frm.email.value = id;
        loginSave.checked = true;
    }

}

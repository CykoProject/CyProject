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


//영화진흥위원회 api

let date = new Date().toLocaleDateString().replace(/\./g, "").replaceAll(" ", "0");
let yesterday = Number(date) - 1;

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
                const actor = data[i].actor;
                const director = data[i].director;
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
let newsElem = document.querySelector(".main-news");
// ../../../java/com/example/CyProject/main/MainNewsApiController
fetch("http://localhost:8090/api/news") //이 경로가 이상한지 패치함수로 안불러와지네..ㅠㅠㅠ
.then(res => res.json())
.then(data => {
    console.log(data.items);
    for(let i=0; i <= data.items.length; i++) {
        newsElem.innerHTML += `<p>${data.items[i].title}</p>`
    }
}).catch(e=> {
    console.log(e.message);
})



const mapContainer = document.querySelector('#map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(35.866159,128.593817), // 지도의 중심좌표
        level: 2, // 지도의 확대 레벨
        mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
    };
// 지도를 생성한다
const map = new kakao.maps.Map(mapContainer, mapOption);
// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(35.866159,128.593817), // 마커의 좌표
    map: map // 마커를 표시할 지도 객체
});
const infowindow = new kakao.maps.InfoWindow({
    content : '<div style="padding:5px;">처음 시작된 곳</div>' // 인포윈도우에 표시할 내용
});
// 인포윈도우를 지도에 표시한다
infowindow.open(map, marker);
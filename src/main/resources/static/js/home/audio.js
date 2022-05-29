const repreAudioElem = document.querySelector('.repre-audio');
const playList = [];
let cnt = 0;
if(repreAudioElem) {
    const audio = document.querySelector('.audio-player');
    const url = new URL(location.href);
    const params = url.searchParams;
    const iuser = params.get("iuser");

    audio.addEventListener('ended', () => {
        cnt = (playList.length - 1) === cnt ? 0 : ++cnt;
        audio.src = playList[cnt].url;
        audio.play();
    });

    audio.addEventListener('play', () => {
        const trArr = document.querySelectorAll('tbody tr');
        trArr.forEach(item => {
            if(item.classList.contains('this-music')) {
                item.classList.remove('this-music');
            }
        });
        trArr[cnt].classList.add('this-music');
    });

    fetch(`/ajax/home/repre/audio?iuser=${iuser}`)
        .then(res => res.json())
        .then(data => {
            if(data) {
                let idx = 0;
                data.forEach(item => {
                    const musicData = {};
                    musicData.imusic = item.imusic.item_id;
                    musicData.nm = item.imusic.nm;
                    musicData.artist = item.imusic.artist;
                    musicData.url = '/pic/bgm/' + item.imusic.file;
                    musicData.idx = idx++;
                    playList.push(musicData);
                });
                const tbodyElem = document.querySelector('.play-list-body');
                playList.forEach(item => {
                    const tr = document.createElement('tr');
                    tr.dataset.imusic = item.imusic;
                    tr.innerHTML = `
                        <td>${item.nm}</td>
                        <td>${item.artist}</td>
                        <td><button class="play-btn">듣기</button></td>
                    `;
                    tbodyElem.appendChild(tr);
                });
                const btnArr = tbodyElem.querySelectorAll('.play-btn');
                btnArr.forEach(item => {
                    item.addEventListener('click', () => {
                        const imusic = parseInt(item.closest('tr').dataset.imusic);
                        playList.forEach(item => {
                            if(imusic === item.imusic) {
                                audio.src = item.url;
                                audio.play();
                                cnt = item.idx;
                            }
                        });
                    });
                });
                audio.src = playList[0].url;
            }
        })
        .catch(e => {
            console.error(e);
        });
}
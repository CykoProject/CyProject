const jukebox = document.querySelector('.home-jukebox');
if(jukebox) {
    const url = new URL(location.href);
    const urlParams = url.searchParams;
    const ihost = urlParams.get('iuser');
    const audioPlayer = document.querySelector('.audio-player');
    const playerNmElem = document.querySelector('.player-nm');
    const playBtnElem = document.querySelector('#play');
    const allCheckBoxElem = document.querySelector('.jukebox-all-check');
    const checkBoxElemArr = document.querySelectorAll('.jukebox-check');
    const repreBtnElem = document.querySelector('.repre-btn');
    const repreRemoveBtnElem = document.querySelector('.repre-remove-btn');

    let playList = [];
    let cnt = 0;
    let loop = true;

    let chkCnt = 0;
    allCheckBoxElem.addEventListener('click', () => {
        checkBoxElemArr.forEach(item => {
            item.checked = allCheckBoxElem.checked === true ? true : false;
            chkCnt = allCheckBoxElem.checked === true ? checkBoxElemArr.length : 0;
        });
    });

    checkBoxElemArr.forEach(item => {
        item.addEventListener('click', () => {
            chkCnt = item.checked === true ? ++chkCnt : --chkCnt;
            allCheckBoxElem.checked = chkCnt === checkBoxElemArr.length ? true : false;
        });
    });

    playBtnElem.addEventListener('click', () => {
        cnt = 0;
        playList = [];
        const musicCheckedArr = document.querySelectorAll('.jukebox-check');
        musicCheckedArr.forEach(item => {
            if(!item.checked) return;
            let dupCnt = 0;
            const parent = item.closest('.music-data');
            const musicArtist = parent.querySelector('.artist').innerText;
            const musicNm = parent.querySelector('.music-nm').innerText;
            const musicUrl = parent.dataset.src;
            const ijukebox = parent.querySelector('.pk').innerText;

            const musicData = {};
            musicData.ijukebox = ijukebox;
            musicData.artist = musicArtist;
            musicData.nm = musicNm;
            musicData.url = musicUrl;

            if(playList.length > 0) {
                playList.forEach(item => {
                    if(item['ijukebox'] === ijukebox) ++dupCnt;
                });
                if(cnt === 0) {
                    playList.push(musicData);
                }
            } else {
                playList.push(musicData);
            }
        });

        audioPlayer.src = '/pic/bgm/'+playList[cnt].url;
        playerNmElem.innerText = playList[cnt].nm + ' - ' + playList[cnt].artist;
        audioPlayer.play();
        ++cnt;
    });

    audioPlayer.addEventListener('ended', () => {
        console.log(playList);
        if(cnt < playList.length) {
            audioPlayer.src = '/pic/bgm/' + playList[cnt].url;
            playerNmElem.innerText = playList[cnt].nm + ' - ' + playList[cnt].artist;
            audioPlayer.play();
            cnt++;
        } else if(cnt === playList.length && loop) {
            cnt = 0;
            audioPlayer.src = '/pic/bgm/' + playList[cnt].url;
            playerNmElem.innerText = playList[cnt].nm + ' - ' + playList[cnt].artist;
            audioPlayer.play();
            cnt++;
        }
    });

    if(repreBtnElem) {
        repreBtnElem.addEventListener('click', () => {
            const repreBool = repreBtnElem.innerText === '배경음악 등록' ? true : false;
            const status = repreBool === true ? '등록' : '제거';
            const data = [];
            const parentArr = [];
            checkBoxElemArr.forEach(item => {
                if (item.checked) {
                    const parent = item.closest('.music-data');
                    parentArr.push(parent);
                    const list = {
                        repre: repreBool,
                        ijukebox: parent.querySelector('.pk').innerText,
                        ihost: ihost
                    };

                    data.push(list);
                }
            });

            fetch('/ajax/home/jukebox/repre', {
                method: 'POST',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({jukeBoxList: data})
            })
                .then(res => res.json())
                .then(data => {
                    if(data.result === 1) {
                        msgSendSuccess(`배경음악 ${status}에 성공했습니다 !`);
                        if(!repreBool) {
                            parentArr.forEach(item => {
                                item.remove();
                            });
                        }
                    }
                })
                .catch(e => {
                    console.error(e);
                });
        });
    }
}
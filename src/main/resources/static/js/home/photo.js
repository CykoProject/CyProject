
/* photo write */
const photoWriteForm = document.querySelector('#photo-form');

if (photoWriteForm) {

    const fileUpload = document.querySelector('.file');

    const photoWriteBtn = document.querySelector('.save-btn');
    photoWriteBtn.addEventListener('click', (e) => {
        const titleValue = photoWriteForm.querySelector('input[name="title"]').value;
        const ctntValue = photoWriteForm.querySelector('textarea').value;
        const fileValue = photoWriteForm.querySelector('#file').value;
        if (titleValue.length === 0) {
            e.preventDefault();
            alert('제목을 작성하세요.');
        }

        if (ctntValue.length === 0) {
            e.preventDefault();
            alert('내용을 작성하세요.');
        }

        if (fileValue.length === 0) {
            e.preventDefault();
            alert('이미지을 첨부하세요.');
        }
    });

    photoWriteForm.imgs.addEventListener('change', (e) => {
        const imgNameInput = document.querySelector('.img-name');
        const filePath = fileUpload.value;

        var fileValue = $(".file").val().split("\\");
        var fileName = fileValue[fileValue.length-1];

        imgNameInput.value = fileName;

        const textareaElem = photoWriteForm.ctnt;

        if (e.target.files.length > 0) {
        }
    });
}


/* photo list */
// const iphoto = document.querySelector('.data-iphoto').dataset.iphoto;
const iphotoList = document.querySelectorAll('.data-iphoto');
console.log(iphotoList);

for (let i = 0; i < iphotoList.length; i++) {
    let iphoto = iphotoList[i].dataset.iphoto;

    const getPhotos = () => {
        fetch(`/ajax/home/photo?iphoto=${iphoto}`)
            .then(res => res.json())
            .then(data => {
                console.log(data);

                const imgContElems = document.querySelectorAll('.imgCont');

                data.forEach(item => {
                    imgContElems[i].innerHTML = `
            <img src='/pic/photos/${item.iphoto}/${item.img}'></img>
        `;
                })
                return imgContElems[i];
            })
            .catch(e => {
                console.log(e);
            })
    }

    getPhotos();
}



const makeImgElem = (data) => {
    const imgContElem = document.querySelector('.imgCont');

    data.forEach(item => {
        imgContElem.innerHTML = `
            <img src='/pic/photos/${item.iphoto}/${item.img}'></img>
        `;
    })
    return imgContElem;
}


/* 수정 */
const modBtnList = document.querySelectorAll('.mod-btn');
if (modBtnList) {
    for (let i = 0; i < modBtnList.length; i++) {
        let modBtn = modBtnList[i];
        let iphoto = modBtn.dataset.iphoto;

        modBtn.addEventListener('click', () => {
            location.href = `/home/photo/write?iphoto=${iphoto}&iuser=${iuser}`;
        })
    }
}

/* 삭제 */
const delBtnList = document.querySelectorAll('.del-btn');
if (delBtnList) {
    for (let i = 0; i < delBtnList.length; i++) {
        let delBtn = delBtnList[i];
        let iphoto = delBtn.dataset.iphoto;

        delBtn.addEventListener('click', (e) => {
            if (confirm('글을 삭제하시겠습니까?')) {
                fetch(`/ajax/home/photo/del?iphoto=${iphoto}&ihost=${iuser}`, {
                    method : 'DELETE',
                    headers : {'Content-Type' : 'application/json'}
                })
                    .then(res => res.json())
                    .then(data => {
                        console.log(data);
                        if(data.result === 1) {
                            location.reload();
                        } else {
                            alert('삭제에 실패했습니다');
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }
        })
    }
}

/* 스크랩 */
const scrapBtnList = document.querySelectorAll('.scrap-btn');
if (scrapBtnList) {
    for (let i = 0; i < scrapBtnList.length; i++) {
        let scrapBtn = scrapBtnList[i];
        let iphoto = scrapBtn.dataset.iphoto;
        let user = scrapBtn.dataset.iuser;

        scrapBtn.addEventListener('click', () => {
            // const param = {
            //     iuser : user,
            //     iphoto : iphoto
            // }

            // console.log(param);

            if (confirm('내 사진첩으로 스크랩 하시겠습니까?')) {
                fetch(`/ajax/home/photo/scrap?iuser=${user}&iphoto=${iphoto}`, {
                    method : 'POST',
                    headers : {'Content-Type' : 'application/json'}
                })
                    .then(res => res.json())
                    .then(data => {
                        console.log(data);
                        if(data.result === 1) {
                            alert('내 사진첩으로 스크랩 되었습니다.');
                        } else {
                            alert('스크랩에 실패했습니다.');
                        }
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }
        })
    }
}

/* 댓글 */
commentObj.parentName = '.photo-data';
commentObj.menu = 'photo';
commentObj.size = 5;
commentObj.init();
commentObj.makeCnt();

// 댓글쓰기
commentObj.writeCmt.init.execute('.ctnt-btn');
commentObj.writeCmt.submit();

// 댓글보기
const commentCountElemArr = document.querySelectorAll('.comment-cnt');
commentCountElemArr.forEach(item => {
    item.addEventListener('click', () => {
        const parent = item.closest('.comment-container');
        const ctntElem = parent.querySelector('.hidden-ctnt');
        if(ctntElem.style.display === 'none' || ctntElem.style.display === '') {
            ctntElem.style.display = 'flex';
        } else {
            ctntElem.style.display = 'none';
        }
    });
});

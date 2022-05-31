
/* photo write */
const photoWriteForm = document.querySelector('#photo-form');

if (photoWriteForm) {

    const fileUpload = document.querySelector('.file');

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


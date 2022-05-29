
/* photo write */
const photoWriteForm = document.querySelector('#photo-form');

if (photoWriteForm) {
    const fileUpload = document.querySelector('#file');

    photoWriteForm.imgs.addEventListener('change', (e) => {
        const filePath = fileUpload.value;
        const imgNameInput = document.querySelector('.img-name');

        imgNameInput.value = filePath;

        const textareaElem = photoWriteForm.ctnt;

        if (e.target.files.length > 0) {
        }
    });
}


/* photo list */
const iphoto = document.querySelector('.data-iphoto').dataset.iphoto;
console.log(iphoto);

fetch(`/ajax/home/photo?iphoto=${iphoto}`)
    .then(res => res.json())
    .then(data => {
        console.log(data);
        makeImgElem(data);
    })
    .catch(e => {
        console.log(e);
    })

const makeImgElem = (data) => {
    const imgContElem = document.querySelector('.imgCont');

    data.forEach(item => {
        imgContElem.innerHTML = `
            <img src='/pic/photos/${item.iphoto}/${item.img}'></img>
        `;
    })
    return imgContElem;
}


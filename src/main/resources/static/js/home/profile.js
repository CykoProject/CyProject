
/* profile write */
const profileForm = document.querySelector('#profile-form');

if (profileForm) {
    const fileUpload = document.querySelector('#file');

    const profileWriteBtn = document.querySelector('.save-btn');
    profileWriteBtn.addEventListener('click', (e) => {
        console.log('ddd');
        const ctntValue = profileForm.querySelector('textarea').value;
        const fileValue = profileForm.querySelector('#file').value;

        if (fileValue.length === 0) {
            e.preventDefault();
            alert('이미지을 첨부하세요.');
        }

        if (ctntValue.length === 0) {
            e.preventDefault();
            alert('내용을 작성하세요.');
        }
    });

    profileForm.profileImg.addEventListener('change', (e) => {
        console.log('dd');
        const imgNameInput = document.querySelector('.img-name');
        const filePath = fileUpload.value;

        var fileValue = $("#file").val().split("\\");
        var fileName = fileValue[fileValue.length-1];

        imgNameInput.value = fileName;

    });
}
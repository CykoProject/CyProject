const msgDetailContainerDetailJs = document.querySelector('.msg-detail-container');
if(msgDetailContainerDetailJs) {
    window.addEventListener('beforeunload', () => {
        opener.location.reload();
    });
}
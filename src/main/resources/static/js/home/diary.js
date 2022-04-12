const diaryELem = document.querySelector('.diary-content');
if(diaryELem) {
    const diaryDelBtnArr = document.querySelectorAll('.diary-del');
    const removeElem = (elem) => {
        elem.remove();
    }

    // 삭제 ========================================================================================================
    const delFetch = (url, elem) => {
        fetch(url, {
            method : 'DELETE',
            headers : {'Content-Type' : 'application/json'}
        })
            .then(res => res.json())
            .then(data => {
                if(data.result === 1) {
                    removeElem(elem);
                }
            })
            .catch(e => {
                console.error(e);
            });
    }

    diaryDelBtnArr.forEach(item => {
        item.addEventListener('click', (e) => {
            if(confirm('정말로 삭제하시겠습니까 ?')) {
                const targetElem = e.target.closest('.diary-data');
                const idiary = e.target.dataset.idiary;
                const url = `/ajax/home/diary/del?idiary=${idiary}`;
                delFetch(url, targetElem);
            }
        });
    });
    // 삭제 ========================================================================================================

    // 신고 ========================================================================================================
    const diaryReportBtnArr = document.querySelectorAll('.diary-report');
    diaryReportBtnArr.forEach(item => {
        item.addEventListener('click', (e) => {
            const targetElem = e.target.closest('.diary-data');
            const ctnt = targetElem.querySelector('.diary-ctnt').innerText;
            // TODO 신고 카테고리 정해야함
            // TODO 신고 사유 모달창
        });
    });
    // 신고 ========================================================================================================

    // 달력 ========================================================================================================
    const diaryCalendarElem = document.querySelector('.diary-calendar');
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    const dateString = year + '-' + month + '-' + day;
    diaryCalendarElem.value = dateString;

    diaryCalendarElem.addEventListener('change', () => {
        // TODO href => orderby rdt
    });
    // 달력 ========================================================================================================
}
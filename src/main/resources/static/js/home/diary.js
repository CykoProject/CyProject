const diaryELem = document.querySelector('.home-container');
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
    const diaryReportModal = document.querySelector('.diary-report-modal');
    const diaryReportModalSaveBtn = document.querySelector('#report-submit-btn');
    diaryReportModalSaveBtn.addEventListener('click', () => {
        const iboard = document.querySelector('#report-iboard').innerText;
        const ihost = document.querySelector('#diary-ihost').value;
        const reason = document.querySelector('.diary-report-textarea').value;
        if(reason.length === 0) {
            alert('1자 이상 작성해 주세요.');
            return;
        }
        const reportData = {
            'iboard' : iboard,
            'iuser' : ihost,
            'reason' : reason
        }
        fetch(`/ajax/home/diary/report`, {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(reportData)
        })
            .then(res => res.json())
            .then(data => {
                if(data.result === 1) {
                    diaryReportCloseBtn.click();
                } else {
                    alert('실패');
                }
            })
            .catch(e => {
                console.error(e);
            });
    });

    diaryReportBtnArr.forEach(item => {
        item.addEventListener('click', (e) => {
            const targetElem = e.target.closest('.diary-data');
            const iboardElem = document.querySelector('#report-iboard');
            const ihostElem = document.querySelector('#diary-ihost');

            const iboard = targetElem.querySelector('#data-idiary').dataset.iboard;
            const ihost = targetElem.querySelector('#data-ihost').dataset.ihost;

            iboardElem.innerText = iboard;
            ihostElem.value = ihost;
            diaryReportModal.style.display = 'flex';
        });
    });

    const diaryReportCloseBtn = document.querySelector('.diary-report-close');
    diaryReportCloseBtn.addEventListener('click', () => {
        const reportCtnt = document.querySelector('.diary-report-textarea');
        reportCtnt.value = '';
        diaryReportModal.style.display = 'none';
    });
    // 신고 ========================================================================================================

    // 달력 ========================================================================================================
    const diaryCalendarElem = document.querySelector('.diary-calendar');
    const today = new Date();
    const year = today.getFullYear();
    const month = ('0' + (today.getMonth() + 1)).slice(-2);
    const day = ('0' + today.getDate()).slice(-2);
    const dateString = year + '-' + month + '-' + day;
    const dateSearchBtn = document.querySelector('.date-search-btn');
    const dateUrl = new URL(window.document.location.href);
    const dateUrlParams = dateUrl.searchParams;
    const rdt = dateUrlParams.get('searchRdt');

    diaryCalendarElem.value = dateString;
    if(rdt != null) {
        diaryCalendarElem.value = rdt;
    }

    dateSearchBtn.addEventListener('click', () => {
        location.href = `/home/diary?iuser=${iuser}&searchRdt=${diaryCalendarElem.value}`;
    });
    // 달력 ========================================================================================================

    // 댓글 ========================================================================================================
    commentObj.parentName = '.diary-data';
    commentObj.menu = 'diary';
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
    // 댓글 ========================================================================================================
}

// 다이어리 작성 ====================================================================================================================
const diaryWriteElem = document.querySelector('#diary-write-elem');
if(diaryWriteElem) {
    const selectFontElem = diaryWriteElem.querySelector('.select-font');
    const diaryWriteTextareaElem = diaryWriteElem.querySelector('.diary-write-textarea');
    let preFontVal = '';
    selectFontElem.addEventListener('change', () => {
        if(preFontVal != '') {
            diaryWriteTextareaElem.classList.remove(preFontVal);
        }
        const selectedOption = selectFontElem[selectFontElem.selectedIndex];
        const fontVal = selectedOption.dataset.font;
        preFontVal = fontVal;
        diaryWriteTextareaElem.classList.add(fontVal);
    });
}
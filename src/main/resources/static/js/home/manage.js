const manageContainerElem = document.querySelector('.manage_container');
if(manageContainerElem) {
    const manageFrmElem = manageContainerElem.querySelector('.manageFrm');
    const tabSubmitBtnElem = manageContainerElem.querySelector('.tab-submit-btn');
    const tabAllCheckElem = manageContainerElem.querySelector('.tab-check');
    const tabIssue = document.querySelector('.tab-issue');
    const tabNameList = [
        'diary', 'photo', 'visit', 'jukebox', 'mini_room'
    ];

    // 탭 check ===================================================================================================
    let tabStatusCnt = 0;
    tabAllCheckElem.addEventListener('click', () => {
        if(!tabAllCheckElem.checked) {
            tabNameList.forEach(item => {
                const tabStatus = manageContainerElem.querySelector(`input[name=${item}]`);
                tabStatusCnt = 0;
                tabStatus.checked = false;
            });
        } else {
            tabNameList.forEach(item => {
                const tabStatus = manageContainerElem.querySelector(`input[name=${item}]`);
                tabStatusCnt = tabNameList.length;
                tabStatus.checked = true;
            });
        }
    });

    tabNameList.forEach(item => {
        const tabStatus = manageContainerElem.querySelector(`input[name=${item}]`);
        if(tabStatus.checked) {
            tabStatusCnt++;
        }
        if(tabStatusCnt === tabNameList.length) {
            tabAllCheckElem.checked = true;
        }

        tabStatus.addEventListener('click', () => {
            if(tabStatus.checked) {
                tabStatusCnt++;
            } else {
                tabStatusCnt--;
                tabAllCheckElem.checked = false;
            }
            if(tabStatusCnt === tabNameList.length) {
                tabAllCheckElem.checked = true;
            }
        });
    });


    tabSubmitBtnElem.addEventListener('click', () => {
        manageFrmElem.submit();
    });

    if(tabIssue.dataset.err) {
        alert(tabIssue.dataset.err);
    }
    // 탭 check ===================================================================================================

}
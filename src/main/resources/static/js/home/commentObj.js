const beforeElem = localStorage.getItem('beforeScroll');
if(beforeElem) {
    document.querySelector('.home-container').scroll(0, parseInt(beforeElem));
    localStorage.clear();
}

const cUrl = new URL(location.href);
const cUrlParams = cUrl.searchParams;

const commentObj = {
    url : '',
    cntUrl : '',

    url : '',
    dataSetName : '#data-iboard',
    menu : '',
    iboard : 0,
    parentName : '',
    parentElem : '',
    parentElemArr : '',

    page : 0,
    size : 5,
    currentPage : 1,
    pageCnt : 5,
    pop : 1,
    startPage : 1,
    lastPage : 5,

    elemName : '.comment-ctnt',
    elemCntName : '.comment-cnt',

    ctntElem : document.querySelector(`${this.name}`),

    myFetch : {
        send : function (fetchObj, cb) {
            return fetchObj
                .then(res => res.json())
                .then(cb)
                .catch(e => { console.log(e) });
        },
        get : function (url, cb, param) {
            if(param) {
                const queryString = '?' + Object.keys(param).map(key => `${key}=${param[key]}`).join('&');
                url += queryString;
            }
            return this.send(fetch(url), cb);
        },
        post : function (url, param, cb) {
            return this.send(fetch(url, {
                method : 'POST',
                headers : {'Content-Type' : 'application/json'},
                body : JSON.stringify(param)
            }), cb);
        }
    },
    init : function () {
        this.parentElemArr = document.querySelectorAll(`${this.parentName}`);

        commentObj.url = `/ajax/home/${commentObj.menu}/cmt/`;
        commentObj.cntUrl = `/ajax/home/${commentObj.menu}/cmt/cnt/`;
        this.parentElemArr.forEach(item => {
            this.iboard = parseInt(item.querySelector(`${this.dataSetName}`).dataset.iboard);
            const elem = item.querySelector(`${this.elemName}`);
            this.myFetch.get(`${this.url}${this.iboard}`, (data) => {
                data.forEach(cmt => {
                    this.makeCmt(cmt, elem);
                });
            }, {
                iboard : this.iboard,
                size : this.size,
                page : this.page
            });
        });
    },
    makePage : function (totalCnt, paginationElem) {
        paginationElem.innerHTML = '';

        const parent = paginationElem.closest(`${this.parentName}`);

        this.pop = Math.ceil(this.currentPage / this.size);
        const maxPage = Math.ceil(totalCnt / this.size);
        const lastPage = this.size * this.pop;
        const startPage = lastPage - (this.size - 1);

        const iboard = parseInt(parent.querySelector(`${this.dataSetName}`).dataset.iboard);

        const span1 = document.createElement('span');
        span1.innerText = '<';
        span1.classList.add('hover-pointer')
        if(startPage !== 1) {
            paginationElem.appendChild(span1);
        }
        span1.addEventListener('click', () => {
            this.parentElem = parent;
            this.currentPage = startPage - 1;
            this.makePage(totalCnt, paginationElem);
            this.getPageList(iboard, this.currentPage - 1);
        });

        for(let i=startPage; i<=(lastPage<maxPage ? lastPage:maxPage); i++) {
            const span = document.createElement('span');
            span.classList.add('hover-pointer');
            if(this.currentPage === i) {
                span.classList.add('selected');
            }

            span.innerText = i;
            paginationElem.appendChild(span);
            span.addEventListener('click', () => {
                this.currentPage = i;
                this.parentElem = parent;
                const page = i-1;
                this.makePage(totalCnt, paginationElem);
                this.getPageList(iboard, page);
            });
        }

        const span2 = document.createElement('span');
        span2.innerText = '>';
        span2.classList.add('hover-pointer');
        if(lastPage < maxPage) {
            paginationElem.appendChild(span2);
        }
        span2.addEventListener('click', () => {
            this.parentElem = parent;
            this.currentPage = lastPage + 1;
            this.makePage(totalCnt, paginationElem);
            this.getPageList(iboard, this.currentPage - 1);
        });
    },

    getPageList : function (iboard, page) {
        this.myFetch.get(`${this.url}${iboard}`, (data) => {
            this.setPageList(data);
        }, {
            page: page,
            size : this.size
        })
    },

    setPageList : function (list) {
        const ctntElem = this.parentElem.querySelector(`${this.elemName}`);
        ctntElem.innerHTML = '';
        list.forEach(item => {
            this.makeCmt(item, ctntElem);
        });
    },
    makeCnt : function () {
        this.parentElemArr.forEach(item => {
            this.iboard = parseInt(item.querySelector(`${this.dataSetName}`).dataset.iboard);
            this.myFetch.get(`${this.cntUrl}${this.iboard}`, (data) => {
                const elem = item.querySelector(`${this.elemCntName}`);
                elem.innerText = `댓글보기(${data.result})`;
                this.makePage(data.result, item.querySelector('.pagination'));
            });
        });
    },
    makeCmt : function (item, elem) {
        const div = document.createElement('div');
        div.classList.add('cmt');
        div.dataset.icmt = item.icmt;
        const p = document.createElement('div');
        const ctntToNewLineVal = item.ctnt.replaceAll('\n', '<br>');
        p.innerHTML = `
            <div class="cmt-word-break">
                <div class="cmt-first-child">
                    <a href="/home?iuser=${item.writer.iuser}">
                        <span class="comment-writer-nm">${item.writer.nm} </span>
                    </a>
                </div>
                <div>
                    <span>${ctntToNewLineVal}</span>
                </div>
            </div>
        `;

        div.appendChild(p);
        elem.appendChild(div);
    },
    writeCmt : {
        url : '/ajax/home/',
        btnElemArr : '',
        btnNm : '',

        ihost : 0,
        loginUserPk : 0,
        iboard : 0,
        init : {
            inputTxtNm : '.ctnt',
            execute : function (btnNm) {
                commentObj.writeCmt.btnElemArr = document.querySelectorAll(`${btnNm}`);
                commentObj.writeCmt.ihost = parseInt(cUrlParams.get('iuser'));
                commentObj.writeCmt.loginUserPk = parseInt(document.querySelector('.home-common-pk').dataset.pk);
                commentObj.writeCmt.url += commentObj.menu + '/cmt/write';
            }
        },
        submit : function () {
            // todo
            this.btnElemArr.forEach(item => {
                item.addEventListener('click', (e) => {
                    const parent = e.target.closest(`${commentObj.parentName}`);
                    const ctnt  = parent.querySelector(`${this.init.inputTxtNm}`).value;
                    this.iboard = parseInt(parent.querySelector(`${commentObj.dataSetName}`).dataset.iboard);
                    commentObj.myFetch.post(this.url, {
                        ihost : {iuser : this.ihost},
                        iboard : this.iboard,
                        writer : {iuser : this.loginUserPk},
                        ctnt : ctnt
                    }, (data) => {
                        if(data.result === 1) {
                            localStorage.setItem('beforeScroll', document.querySelector('.home-container').scrollTop);
                            location.reload();
                        }
                    });
                });
            });
        }
    }
}
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
    parentElemArr : '',

    page : 0,
    size : 5,
    currentPage : 0,
    pageCnt : 5,
    pop : 1,
    startPage : 1,
    lastPage : 5,

    // elemName : document.querySelector('.comment-ctnt'),
    elemName : '.comment-ctnt',
    // elemCntName : document.querySelector('.comment-cnt'),
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
        const maxPage = Math.ceil(totalCnt / this.size);
        const lastPage = this.size * this.pop;
        const startPage = lastPage - (this.size - 1);

        const span1 = document.createElement('span');
        span1.innerText = '<';
        if(this.pop > 1) {
            paginationElem.appendChild(span1);
        }
        span1.addEventListener('click', (e) => {
            const parent = e.target.closest(`${this.parentName}`);
            this.pop -= 1;
            this.currentPage = startPage - (this.size + 1);
            this.getList(totalCnt, parent, paginationElem);
        });

        for(let i=startPage; i<=(lastPage<maxPage ? lastPage:maxPage); i++) {
            const span = document.createElement('span');
            span.innerText = i;
            if(this.currentPage+1 === i) {
                console.log('same');
                span.classList.add('selected');
            }

            paginationElem.appendChild(span);
            span.addEventListener('click', (e) => {
                this.currentPage = i;
                this.iboard = e.target.closest(`${this.parentName}`).querySelector('#data-iboard').dataset.iboard;
                const parent = e.target.closest(`${this.parentName}`);
                this.myFetch.get(`/ajax/home/${this.menu}/cmt/${this.iboard}`, (data) => {

                    const elem = parent.querySelector(`${this.elemName}`);
                    elem.innerHTML = '';
                    data.forEach(item => {
                        this.makeCmt(item, elem);
                    });

                    this.pop = Math.ceil( i / this.size);
                    this.makePage(totalCnt, paginationElem);
                }, {
                    page : i-1,
                    size : this.size
                });
            });
        }

        const span2 = document.createElement('span');
        span2.innerText = '>';
        if(lastPage < maxPage) {
            paginationElem.appendChild(span2);
        }
        span2.addEventListener('click', (e) => {
            const parent = e.target.closest(`${this.parentName}`);

            this.pop += 1;
            this.currentPage = lastPage;

            this.getList(totalCnt, parent, paginationElem);
        });
    },

    getList : function (totalCnt, elem, pageElem) {
        this.iboard = parseInt(elem.querySelector(`${this.dataSetName}`).dataset.iboard);
        this.myFetch.get(`/ajax/home/${this.menu}/cmt/${this.iboard}`, (data) => {
            elem = elem.querySelector(`${this.elemName}`);
            elem.innerHTML = '';
            data.forEach(item => {
                this.makeCmt(item, elem);
            });

            this.makePage(totalCnt, pageElem);
        }, {
            page : this.currentPage,
            size : this.size
        })
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
        const date = new Date(item.rdt);
        const srcVal = item.writer.img;
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
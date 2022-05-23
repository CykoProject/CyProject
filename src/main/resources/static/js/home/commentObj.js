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

    page : 0,
    size : 5,
    url : '',
    dataSetName : '#data-iboard',
    menu : '',
    iboard : 0,
    parentName : '',
    parentElemArr : '',

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
        const maxPage = Math.ceil(totalCnt / this.size);
        for(let i=1; i<=maxPage; i++) {
            const span = document.createElement('span');
            span.innerText = i;
            paginationElem.appendChild(span);
            span.addEventListener('click', (e) => {
                this.iboard = e.target.closest(`${this.parentName}`).querySelector('#data-iboard').dataset.iboard;
                this.myFetch.get(`/ajax/home/${this.menu}/cmt/${this.iboard}`, (data) => {
                    this.parentElemArr.forEach(item => {
                        const elem = item.querySelector(`${this.elemName}`);
                        elem.innerHTML = '';
                        data.forEach(item => {
                            this.makeCmt(item, elem);
                        });
                    });
                }, {
                    page : i-1,
                    size : this.size
                })
            });
        }
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
        const src = srcVal === null ? '/img/defaultProfileImg.jpeg' : `/pic/profile/${srcVal}`;
        p.innerHTML = `
            <div class="cmt-word-break">
                <div class="cmt-first-child">
                    <a href="/home?iuser=${item.writer.iuser}"><span class="comment-writer-nm">${item.writer.nm} </span></a>
                </div>
                <div>
                    <span>${item.ctnt}</span>
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
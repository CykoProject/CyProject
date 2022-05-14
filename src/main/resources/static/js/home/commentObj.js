const commentObj = {
    page : 0,
    size : 5,
    url : '',
    dataSetName : '',
    menu : '',
    iboard : 0,
    parentName : '',
    parentElemArr : '',
    elemName : '',
    elemCntName : '',
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
        }
    },
    init : function () {
        this.parentElemArr = document.querySelectorAll(`${this.parentName}`);
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
                this.iboard = e.target.closest('.diary-data').querySelector('#data-idiary').dataset.iboard;
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
            this.myFetch.get(`${this.url}${this.iboard}`, (data) => {
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
        const p = document.createElement('p');
        p.innerText = item.ctnt;
        div.appendChild(p);
        elem.appendChild(div);
    }
}
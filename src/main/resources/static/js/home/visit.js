const visit_url = new URL(location.href);
const visit_urlParams = visit_url.searchParams;
const ihost = visit_urlParams.get("iuser");
const loginUserPk = document.querySelector('.data-pk').dataset.loginuser;
console.log(loginUserPk);

const getList = (ihost) => {
    fetch(`/ajax/home/visit?ihost=${ihost}`)
        .then(res => res.json())
        .then(data => {
            setList(data);
        })
        .catch(e => {
            console.error(e);
        });
}

const visitContentsElem = document.querySelector('.visit-contents');
const setList = (list) => {
    list.forEach(item => {
        const rdtArr = item.rdt.split("T"); // [YYYY-MM-DD, HH:MM:SS]
        const divElem = document.createElement('div');
        if(item.secret) {
            if (loginUserPk === ihost || loginUserPk === item.ihost) {
                divElem.innerHTML = `
                    <div class="visit-secret-bar">
                        <span>(${rdtArr[0]} ${rdtArr[1]})</span>
                    </div>
                    <div class="visit-ctnt">
                        <span>${item.ctnt}</span>
                    </div>
                `;
            } else {
                divElem.innerHTML = `
                    <div class="visit-secret-bar">
                        <span>(${rdtArr[0]} ${rdtArr[1]})</span>
                    </div>
                    <div class="visit-ctnt">
                        <span class="secret-ctnt">비밀이야 (이 글은 홈 주인과 작성자만 볼 수 있어요.)</span>
                    </div>
                `;
            }
        } else {
            divElem.innerHTML = `
                <div class="visit-date-bar">
                    <span>(${rdtArr[0]} ${rdtArr[1]})</span>
                </div>
                <div class="visit-ctnt">
                    <span>${item.ctnt}</span>
                </div>
            `;
        }
        visitContentsElem.appendChild(divElem);
    });
}

getList(ihost);
let OrderByRdtDesc = document.querySelector(".option-date-recent");
let OrderByRdtAsc = document.querySelector(".option-date-old");
let OrderByPriceAsc = document.querySelector(".option-price-low");
let OrderByPriceDesc = document.querySelector(".option-price-high");

let shoppingItemsElem = document.querySelector(".shopping-items");

let sortStatus = localStorage.getItem('sort') !== null ? localStorage.getItem('sort') : '';

const sort = localStorage.getItem('sort');
const page = localStorage.getItem('page');

//url 파라미터 받기
function getParameterByName(name) {
    const url = new URL(location.href);
    const params = url.searchParams;
    const results = params.get(name);
    return results;
}

//페이징 숫자 눌렀을 때
if(localStorage.getItem('url')) {
    let url = localStorage.getItem('url');
    if(getParameterByName('search') !== null) {
        let page = getParameterByName('page');
        let category = getParameterByName('category');
        const data = {
            'search': getParameterByName("search"),
            'page' : page,
            'category' : category
        }
        console.log(url);
        fetch((url), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                shoppingItemsElem.innerHTML = null;
                for (let i = 0; i < data.length; i++) {
                    shoppingItemsElem.innerHTML += `
                <div class="item">
                <img src="${data[i].file}" class="item-img" alt="">
                <div class="item_id" style="display: none" data-set="${data[i].item_id}"></div>
                        <span class="item-nm">${data[i].nm}</span>
                        <span class="dotori-span">
                            <img class="dotori" src="https://littledeep.com/wp-content/uploads/2020/12/Acorn-illustration-png-1024x853.png" alt="">
                            <span class="item-price">${data[i].price}</span>
                        </span>
                        <div class="add-cart" style="display: inline"><i class="fa-solid fa-cart-plus"></i></div>
                    </div>
                `
                }
                document.querySelectorAll(".add-cart").forEach((item) => item.addEventListener("click", () => {
                    if (iUser > 0) {

                        const data = {
                            "iuser": parseInt(iUser),
                            "item_id": parseInt(item.parentElement.querySelector(".item_id").dataset.set)
                        }
                        console.log(data);
                        fetch("/cart/add", {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(data)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                msgAlarm();
                            })
                            .catch(e => {
                                console.error(e);
                            });
                    } else {
                        alert("장바구니에 담기 위해서 로그인을 해주세요");
                        location.href = "/";
                    }
                }))

            }).catch(e => {
            console.log(e)
        })

    } else {
        fetch(url)
            .then(res => res.json())
            .then(data => {
                shoppingItemsElem.innerHTML = null;
                for (let i = 0; i < data.length; i++) {
                    shoppingItemsElem.innerHTML += `
                <div class="item">
                <img src="${data[i].file}" class="item-img" alt="">
                <div class="item_id" style="display: none" data-set="${data[i].item_id}"></div>
                        <span class="item-nm">${data[i].nm}</span>
                        <span class="dotori-span">
                            <img class="dotori" src="https://littledeep.com/wp-content/uploads/2020/12/Acorn-illustration-png-1024x853.png" alt="">
                            <span class="item-price">${data[i].price}</span>
                        </span>
                        <div class="add-cart" style="display: inline"><i class="fa-solid fa-cart-plus"></i></div>
                    </div>
                `
                }
                document.querySelectorAll(".add-cart").forEach((item) => item.addEventListener("click", () => {
                    if (iUser > 0) {

                        const data = {
                            "iuser": parseInt(iUser),
                            "item_id": parseInt(item.parentElement.querySelector(".item_id").dataset.set)
                        }
                        console.log(data);
                        fetch("/cart/add", {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(data)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                msgAlarm();
                            })
                            .catch(e => {
                                console.error(e);
                            });
                    } else {
                        alert("장바구니에 담기 위해서 로그인을 해주세요");
                        location.href = "/";
                    }
                }))
            }).catch(e => {
            console.log(e)
        })
    }
    localStorage.clear();
}

function callOrderedItems(btn, name) {
    btn.addEventListener("click", () => {
        sortStatus = name;
        let page = getParameterByName("page");

        let url = `/shopping/api/${name}`;
        if(localStorage.getItem('url')) {
            url = localStorage.getItem('url');
        }
        if (page) {
            url = `/shopping/api/${name}?page=`+page;
        }
        fetch(url)
            .then(res => res.json())
            .then(data => {
                console.log(data);
                shoppingItemsElem.innerHTML = null;
                for (let i = 0; i < data.length; i++) {
                    shoppingItemsElem.innerHTML += `
                <div class="item">
                <img src="${data[i].file}" class="item-img" alt="">
                <div class="item_id" style="display: none" data-set="${data[i].item_id}"></div>
                        <span class="item-nm">${data[i].nm}</span>
                        <span class="dotori-span">
                            <img class="dotori" src="https://littledeep.com/wp-content/uploads/2020/12/Acorn-illustration-png-1024x853.png" alt="">
                            <span class="item-price">${data[i].price}</span>
                        </span>
                        <div class="add-cart" style="display: inline"><i class="fa-solid fa-cart-plus"></i></div>
                    </div>
                `
                }
                document.querySelectorAll(".add-cart").forEach((item) => item.addEventListener("click", () => {
                    if (iUser > 0) {

                        const data = {
                            "iuser": parseInt(iUser),
                            "item_id": parseInt(item.parentElement.querySelector(".item_id").dataset.set)
                        }
                        console.log(data);
                        fetch("/cart/add", {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(data)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                msgAlarm();
                            })
                            .catch(e => {
                                console.error(e);
                            });
                    } else {
                        alert("장바구니에 담기 위해서 로그인을 해주세요");
                        location.href = "/";
                    }
                }))
            }).catch(e => {
                console.log(e)
            }
        )
    })
}

function callSearchOrderedItemsAll(btn, name) {
    sortStatus = name;
    let page = getParameterByName("page");

    let url = `/shopping/api/${name}`;
    if(localStorage.getItem('url')) {
        url = localStorage.getItem('url');
    }
    if (page) {
        url = `/shopping/api/${name}?page=`+page;
    }
    const data = {
        'search': getParameterByName("search")
    }
        fetch((url), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                shoppingItemsElem.innerHTML = null;
                for (let i = 0; i < data.length; i++) {
                    shoppingItemsElem.innerHTML += `
                <div class="item">
                <img src="${data[i].file}" class="item-img" alt="">
                <div class="item_id" style="display: none" data-set="${data[i].item_id}"></div>
                        <span class="item-nm">${data[i].nm}</span>
                        <span class="dotori-span">
                            <img class="dotori" src="https://littledeep.com/wp-content/uploads/2020/12/Acorn-illustration-png-1024x853.png" alt="">
                            <span class="item-price">${data[i].price}</span>
                        </span>
                        <div class="add-cart" style="display: inline"><i class="fa-solid fa-cart-plus"></i></div>
                    </div>
                `
                }
                document.querySelectorAll(".add-cart").forEach((item) => item.addEventListener("click", () => {
                    if (iUser > 0) {

                        const data = {
                            "iuser": parseInt(iUser),
                            "item_id": parseInt(item.parentElement.querySelector(".item_id").dataset.set)
                        }
                        console.log(data);
                        fetch("/cart/add", {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(data)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                msgAlarm();
                            })
                            .catch(e => {
                                console.error(e);
                            });
                    } else {
                        alert("장바구니에 담기 위해서 로그인을 해주세요");
                        location.href = "/";
                    }
                }))

            }).catch(e => {
                console.log(e)
            })
}

function callSearchOrderedItems(btn, name) {
    sortStatus = name;

    let page = getParameterByName("page");

    let url = `/shopping/api/${name}`;
    if(localStorage.getItem('url')) {
        url = localStorage.getItem('url');
    }
    if (page) {
        url = `/shopping/api/${name}?page=`+page;
    }
    const data = {
        'category': getParameterByName("category"),
        'search': getParameterByName("search")
    }
    btn.addEventListener('click', () => {
        console.log(data);
    })

        console.log(url);
        console.log(data);
        fetch((url), {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                shoppingItemsElem.innerHTML = null;
                for (let i = 0; i < data.length; i++) {
                    shoppingItemsElem.innerHTML += `
                <div class="item">
                <img src="${data[i].file}" class="item-img" alt="">
                        <div class="item_id" style="display: none" data-set="${data[i].item_id}"></div>
                        <span class="item-nm">${data[i].nm}</span>
                        <span class="dotori-span">
                            <img class="dotori" src="https://littledeep.com/wp-content/uploads/2020/12/Acorn-illustration-png-1024x853.png" alt="">
                            <span class="item-price">${data[i].price}</span>
                        </span>
                        <div class="add-cart" style="display: inline"><i class="fa-solid fa-cart-plus"></i></div>
                    </div>
                `
                }
                document.querySelectorAll(".add-cart").forEach((item) => item.addEventListener("click", () => {
                    if (iUser > 0) {

                        const data = {
                            "iuser": parseInt(iUser),
                            "item_id": parseInt(item.parentElement.querySelector(".item_id").dataset.set)
                        }
                        console.log(data);
                        fetch("/cart/add", {
                            method: 'POST',
                            headers: {'Content-Type': 'application/json'},
                            body: JSON.stringify(data)
                        })
                            .then(res => res.json())
                            .then(data => {
                                console.log(data);
                                msgAlarm();
                            })
                            .catch(e => {
                                console.error(e);
                            });
                    } else {
                        alert("장바구니에 담기 위해서 로그인을 해주세요");
                        location.href = "/";
                    }
                }))

            }).catch(e => {
                console.log(e)
            }
        )
}

if (location.href.indexOf("search") === -1) {
    console.log("1")

    callOrderedItems(OrderByRdtDesc, 'allOrderByRdtDesc');
    callOrderedItems(OrderByRdtAsc, 'allOrderByRdtAsc');
    callOrderedItems(OrderByPriceAsc, 'allOrderByPriceAsc');
    callOrderedItems(OrderByPriceDesc, 'allOrderByPriceDesc');
} else if (getParameterByName("category") == 0) {
    console.log("0")
    OrderByRdtAsc.addEventListener("click", ()=> {
        callSearchOrderedItemsAll(OrderByRdtAsc, "searchOrderByRdtAscAll");
    })
    OrderByPriceDesc.addEventListener("click", ()=> {
        callSearchOrderedItemsAll(OrderByPriceDesc, "searchOrderByPriceDescAll");
    })
    OrderByPriceAsc.addEventListener("click", ()=> {
        callSearchOrderedItemsAll(OrderByPriceAsc, "searchOrderByPriceAscAll");
    })
    OrderByRdtDesc.addEventListener("click", ()=> {
        callSearchOrderedItemsAll(OrderByRdtDesc, "searchOrderByRdtDescAll");
    })
} else {
    console.log("2")
    OrderByRdtAsc.addEventListener("click", ()=> {
        callSearchOrderedItems(OrderByRdtAsc, 'searchOrderByRdtAsc');
    })
    OrderByPriceAsc.addEventListener("click", ()=> {
        callSearchOrderedItems(OrderByPriceAsc, 'searchOrderByPriceAsc');
    })
    OrderByPriceDesc.addEventListener("click", ()=> {
        callSearchOrderedItems(OrderByPriceDesc, 'searchOrderByPriceDesc');
    })
    OrderByPriceDesc.addEventListener("click", ()=> {
        callSearchOrderedItems(OrderByRdtDesc, 'searchOrderByRdtDesc');
    })
}

//장바구니 넣기
let addCart = document.querySelectorAll(".add-cart");

if (document.querySelector("#loginUserPk").dataset.iuser == null) {
    let iUser = 0;
} else {
    let iUser = document.querySelector("#loginUserPk").dataset.iuser;
}

const msgAlarm = () => {
    const divElem = document.createElement('div');
    divElem.classList.add('msg-alarm');
    divElem.innerHTML = `
                <span>장바구니에 추가</span>
            `;
    const header = document.querySelector('.header');
    divElem.addEventListener('click', () => {
        location.href = '/shopping/cart';
    });
    header.appendChild(divElem);

    let setTimeOut = setTimeout(() => {
        divElem.remove();
    }, 3000);

    divElem.addEventListener('mouseover', () => {
        clearTimeout(setTimeOut);
    });

    divElem.addEventListener('mouseout', () => {
        setTimeOut = setTimeout(() => {
            divElem.remove();
        }, 3000);
    });
}

addCart.forEach((item) => item.addEventListener("click", () => {
    if (iUser > 0) {

        const data = {
            "iuser": parseInt(iUser),
            "item_id": parseInt(item.parentElement.querySelector(".item_id").textContent)
        }
        console.log(data);
        fetch("/cart/add", {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                console.log(data);
                msgAlarm();
            })
            .catch(e => {
                console.error(e);
            });
    } else {
        alert("장바구니에 담기 위해서 로그인을 해주세요");
        location.href = "/";
    }
}))

//페이징

let paginationElem = document.querySelector(".pagination");
console.log(paginationElem)
let resultsCount = Number(document.querySelector(".count-number").textContent);
console.log(resultsCount)
let currentUrl = window.location.href;
console.log(currentUrl)

if (currentUrl.includes("search")) {
    let urlSplit = currentUrl.split('&', 2);
    currentUrl = urlSplit[0]+'&'+urlSplit[1];
    function goToNumber(i) {
        if(sortStatus !== '') {
            console.log(sortStatus)
            localStorage.setItem('url', `/shopping/api/${sortStatus}?page=${i}`);
            localStorage.setItem('sort', sortStatus);
        }
        location.href = currentUrl + '&page=' + i;

        makePagingNumber();
    }

} else {
    let urlSplit = currentUrl.split('?', 2);
    currentUrl = urlSplit[0];
    console.log(currentUrl);
    function goToNumber(i) {
        if(sortStatus !== '') {
            console.log(sortStatus)
            localStorage.setItem('url', `/shopping/api/${sortStatus}?page=${i}`);
            localStorage.setItem('sort', sortStatus);
        }
        location.href = currentUrl + '?page=' + i;
    }
}


let pagingNum = Math.ceil(resultsCount/5);
let pagingDefaultNum = 5;

let pagingCurrentNum = 1;
if (new URL(location.href).searchParams.get("page")) {
    pagingCurrentNum = parseInt(getParameterByName("page")) - 1;
}


let pagingUnit = 1;
if (new URL(location.href).searchParams.get("page")) {
    pagingUnit = Math.floor(parseInt(getParameterByName("page"))/pagingDefaultNum) + 1;
}

let maxPage = pagingUnit * pagingDefaultNum;
let minPage = ((pagingUnit - 1) * pagingDefaultNum) + 1;

if (getParameterByName("page")) {
    pagingCurrentNum = parseInt(getParameterByName("page")) + 1;
    maxPage = (Math.ceil(pagingCurrentNum/pagingDefaultNum)) * pagingDefaultNum
    minPage = maxPage - 4;

    console.log(minPage)
    console.log(maxPage)
}

const makePagingNumber = () => {
    console.log(pagingCurrentNum);
    console.log(maxPage);
    paginationElem.innerHTML = '';
    if (pagingUnit == 1) {
        paginationElem.innerHTML += `
    <p class="prev" style="pointer-events:none"><</p>
    `
    } else  {
        paginationElem.innerHTML += `
    <p class="prev"><</p>
    `
    }
    for (let i = minPage; i <= (pagingNum > maxPage ? maxPage : pagingNum); i++) {
        paginationElem.innerHTML += `
        <span onclick="goToNumber(${i-1})" class="number">${i}</span>
        `;
    }
    if (maxPage >= pagingNum) {
        paginationElem.innerHTML += `
    <p class="next" style="pointer-events:none">></p>
    `
    } else {
        paginationElem.innerHTML += `
    <p class="next">></p>
    `
    }
    let prevBtn = paginationElem.querySelector(".prev");
    let nextBtn = paginationElem.querySelector(".next");

    if (pagingUnit > 1) {
        prevBtn.addEventListener("click", ()=> {
            console.log("prev")
            pagingUnit--;
            pagingCurrentNum = ((pagingUnit-1)*pagingDefaultNum) + 1;
            maxPage = pagingUnit * pagingDefaultNum;

            paginationElem.innerHTML = '';
            goToNumber(pagingCurrentNum-1);
        })
    }
if (pagingNum > maxPage) {
    nextBtn.addEventListener("click", ()=> {
        console.log("next")
        pagingUnit++;
        pagingCurrentNum = ((pagingUnit-1)*pagingDefaultNum) + 1;
        maxPage = pagingUnit * pagingDefaultNum;

        paginationElem.innerHTML = '';
        goToNumber(pagingCurrentNum-1);
    })
}

    document.querySelectorAll(".number")[0].style.fontWeight= "bold";
    document.querySelectorAll(".number")[0].style.fontSize = "large";
    if(new URL(location.href).searchParams.get("page")) {
        document.querySelectorAll(".number")[0].style.fontWeight= "";
        document.querySelectorAll(".number")[0].style.fontSize = "";
        let i = new URL(location.href).searchParams.get("page");
        console.log(i)
        document.querySelectorAll(".number").forEach((item)=> {
            if(item.textContent == parseInt(i) + 1) {
                item.style.fontWeight= "bold";
                item.style.fontSize = "large";
            }
        })
        // console.log(((pagingUnit-1)*pagingDefaultNum))
        // document.querySelectorAll(".number")[i-((pagingUnit-1)*pagingDefaultNum)].style.fontWeight= "bold";
        // document.querySelectorAll(".number")[i-((pagingUnit-1)*pagingDefaultNum)].style.fontSize = "large";
    }
}
makePagingNumber();
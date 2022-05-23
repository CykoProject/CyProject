let OrderByRdtDesc = document.querySelector(".option-date-recent");
let OrderByRdtAsc = document.querySelector(".option-date-old");
let OrderByPriceAsc = document.querySelector(".option-price-low");
let OrderByPriceDesc = document.querySelector(".option-price-high");

let shoppingItemsElem = document.querySelector(".shopping-items");


//url 파라미터 받기
function getParameterByName(name) {
    const url = new URL(location.href);
    const params = url.searchParams;
    const results = params.get(name);
    console.log(results);
    return results;
}

function callOrderedItems(btn, name) {
    btn.addEventListener("click", () => {
        fetch(`/shopping/api/${name}`)
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
                        <span><span class="item-price">${data[i].price}</span>원</span>
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
    const data = {
        'search': getParameterByName("search")
    }
    btn.addEventListener('click', () => {
        console.log(data);
    })

    btn.addEventListener("click", () => {
        fetch(`/shopping/api/${name}`, {
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
                        <span><span class="item-price">${data[i].price}</span>원</span>
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

function callSearchOrderedItems(btn, name) {
    const data = {
        'category': getParameterByName("category"),
        'search': getParameterByName("search")
    }
    btn.addEventListener('click', () => {
        console.log(data);
    })

    btn.addEventListener("click", () => {
        fetch(`/shopping/api/${name}`, {
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
                        <span><span class="item-price">${data[i].price}</span>원</span>
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

if (location.href.indexOf("search") === -1) {
    console.log("1")
    callOrderedItems(OrderByRdtDesc, 'allOrderByRdtDesc');
    callOrderedItems(OrderByRdtAsc, 'allOrderByRdtAsc');
    callOrderedItems(OrderByPriceAsc, 'allOrderByPriceAsc');
    callOrderedItems(OrderByPriceDesc, 'allOrderByPriceDesc');
} else if (getParameterByName("category") == 0) {
    console.log("0")
    callSearchOrderedItemsAll(OrderByRdtDesc, "searchOrderByRdtDescAll");
    callSearchOrderedItemsAll(OrderByRdtAsc, "searchOrderByRdtAscAll");
    callSearchOrderedItemsAll(OrderByPriceDesc, "searchOrderByPriceDescAll");
    callSearchOrderedItemsAll(OrderByPriceAsc, "searchOrderByPriceAscAll");
} else {
    console.log("2")
    callSearchOrderedItems(OrderByRdtDesc, 'searchOrderByRdtDesc');
    callSearchOrderedItems(OrderByRdtAsc, 'searchOrderByRdtAsc');
    callSearchOrderedItems(OrderByPriceAsc, 'searchOrderByPriceAsc');
    callSearchOrderedItems(OrderByPriceDesc, 'searchOrderByPriceDesc');
}

//장바구니 넣기
let addCart = document.querySelectorAll(".add-cart");
let iUser = document.querySelector("#loginUserPk").dataset.iuser;

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

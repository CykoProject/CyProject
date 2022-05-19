let allSelectElem = document.querySelector(".cart-items-select > input");

let cartItemSelectElems = document.querySelectorAll(".cart-item-select > input");

const saveTotalprice = parseInt(document.querySelector('.total-price').innerText);

let selectedItemCntArr = []; // 9
function cartItemsCheck() {
    if (allSelectElem.checked === false) {
        cartItemSelectElems.forEach((item)=> {
            item.checked = false;
            selectedItemCntArr = [];
            document.querySelector('.total-price').innerText = 0;
        })
    } else {
        cartItemSelectElems.forEach((item) => {
            item.checked = true;
            if(selectedItemCntArr.length !== cartItemSelectElems.length) {
                selectedItemCntArr.push(item);
            }

        })
        document.querySelector('.total-price').innerText = numberWithCommas(saveTotalprice);

        cartItemSelectElems.forEach((item) => {
            item.addEventListener("click", (e) => {
                let selectedItemCnt = selectedItemCntArr.filter((item) => item.checked !== false)
                let selectedItemPriceSum = 0;
                if (selectedItemCnt.length === cartItemSelectElems.length) {
                    allSelectElem.checked = true;

                } else if (selectedItemCnt == null) {
                    selectedItemPriceSum = 0;
                }
                else {
                    allSelectElem.checked = false;
                    selectedItemCnt.forEach((item)=> {
                        let selectedItemPrice = item.closest(".cart-item").querySelector(".cart-item-total-price").textContent.split(",").join("");
                        console.log(selectedItemPrice)
                        selectedItemPriceSum += parseInt(selectedItemPrice);
                        console.log(selectedItemPriceSum)
                    })
                    document.querySelector('.total-price').innerText = numberWithCommas(selectedItemPriceSum);

                }
            })
        })

    }
}
cartItemsCheck();

allSelectElem.addEventListener("click", ()=> {
    cartItemsCheck();
})

//장바구니 총 합


let cartItemAllPrice = document.querySelector(".cart-item-all-price > span");
let cartItemAllPriceValue = cartItemAllPrice.textContent;

cartItemAllPrice.innerText = numberWithCommas(cartItemAllPriceValue);

//장바구니 안 상품 삭제

let cartItemDeleteElem = document.querySelectorAll(".cart-item-delete");
let iuser = document.querySelector("#loginUserPk").dataset.iuser;

cartItemDeleteElem.forEach((item)=> {

    item.addEventListener("click", ()=> {
        const cartItemId = item.closest(".cart-item").querySelector(".item_id").textContent;
        let cartItemTotalPrice = item.closest(".cart-item").querySelector(".cart-item-total-price").textContent.split(",").join("");


        const data = {
            "iuser" : iuser,
            "item_id" : cartItemId
        }

        fetch("/cart/delete", {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify(data)
        }).then(res => res.json())
            .then(data => {
                console.log(data);
                alert("상품을 장바구니에서 삭제하였습니다.");
                item.closest(".cart-item").remove();
                cartItemAllPrice.innerText = numberWithCommas(cartItemAllPriceValue - cartItemTotalPrice);
                cartItemAllPriceValue = cartItemAllPriceValue - cartItemTotalPrice;
            })
            .catch(e=> {
                console.error(e)
            });
    })
})

//정규식 천단위 콤마
function numberWithCommas(x) { return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); };

//장바구니 안 상품 수량 증가

let cartItemPlusElem = document.querySelectorAll(".cart-item-plus");

cartItemPlusElem.forEach((item)=> {
    item.addEventListener("click", ()=> {
        const cartItemId = item.closest(".cart-item").querySelector(".item_id").textContent;
        let cartItemCnt = item.closest(".cart-item-total").querySelector(".cart-item-cnt").textContent;
        let cartItemTotalPrice = item.closest(".cart-item").querySelector(".cart-item-total-price");
        let cartItemPrice = item.closest(".cart-item").querySelector(".cart-item-price");
        let cartItemPriceValue = item.closest(".cart-item").querySelector(".cart-item-price").textContent.split(",").join("");

        const data = {
            "iuser" : iuser,
            "item_id" : cartItemId
        }

        fetch("/cart/add", {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body: JSON.stringify(data)
        })
            .then(res => res.json())
            .then(data => {
                cartItemCnt++;
                item.closest(".cart-item-total").querySelector(".cart-item-cnt").innerText = cartItemCnt;
                cartItemTotalPrice.innerText = numberWithCommas(cartItemPriceValue * cartItemCnt);
                cartItemAllPrice.innerText = numberWithCommas(parseInt(cartItemAllPriceValue) + parseInt(cartItemPriceValue));
                cartItemAllPriceValue = parseInt(cartItemAllPriceValue) + parseInt(cartItemPriceValue);
            })
            .catch(e => {
                console.error(e);
            });
    })
})

//장바구니 안 상품 수량 감소

let cartItemMinusElem = document.querySelectorAll(".cart-item-minus");

cartItemMinusElem.forEach((item)=> {
    item.addEventListener("click", ()=> {
        const cartItemId = item.closest(".cart-item").querySelector(".item_id").textContent;
        let cartItemCnt = item.closest(".cart-item-total").querySelector(".cart-item-cnt").textContent;
        let cartItemTotalPrice = item.closest(".cart-item").querySelector(".cart-item-total-price");
        let cartItemPrice = item.closest(".cart-item").querySelector(".cart-item-price");
        let cartItemPriceValue = item.closest(".cart-item").querySelector(".cart-item-price").textContent.split(",").join("");

        if (cartItemCnt > 1) {

            const data = {
                "iuser" : iuser,
                "item_id" : cartItemId
            }

            fetch("/cart/subtract", {
                method : 'POST',
                headers : {'Content-Type' : 'application/json'},
                body: JSON.stringify(data)
            })
                .then(res => res.json())
                .then(data => {
                    console.log(data);
                    cartItemCnt--;
                    item.closest(".cart-item-total").querySelector(".cart-item-cnt").innerText = cartItemCnt;
                    cartItemTotalPrice.innerText = numberWithCommas(cartItemPriceValue * cartItemCnt);
                    cartItemAllPrice.innerText = numberWithCommas(cartItemAllPriceValue - parseInt(cartItemPriceValue));
                    cartItemAllPriceValue = cartItemAllPriceValue - parseInt(cartItemPriceValue);
                })
                .catch(e => {
                    console.error(e);
                });
        }


    })
})


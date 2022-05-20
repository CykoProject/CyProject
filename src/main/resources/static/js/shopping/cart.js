let allSelectElem = document.querySelector(".cart-items-select > input");

let cartItemSelectElems = document.querySelectorAll(".cart-item-select > input");

let cartItemAllPrice = document.querySelector(".cart-item-all-price > span");

const initAllPrice = () => {
    let price = 0;
    const checkArr = document.querySelectorAll(".cart-item-select > input");
    checkArr.forEach(item => {
        const isChecked = item.checked;
        if(isChecked === true) {
            price += parseInt(item.closest(".cart-item").querySelector(".cart-item-total-price").innerText.split(',').join(''));
        }
    });

    cartItemAllPrice.innerText = numberWithCommas(checkArr.length === 0 ? 0 : price);
}

let selectedItemCntArr = []; // 9
// cartItemSelectElems.forEach((item)=> {
// selectedItemCntArr.push(item)
// })
console.log("초기 배열" + selectedItemCntArr)
function cartItemsCheck() {

    if (allSelectElem.checked === false) {
        cartItemSelectElems.forEach((item)=> {
            item.checked = false;
            initAllPrice();
            selectedItemCntArr = [];
            document.querySelector('.total-price').innerText = 0;
        })
        console.log("함수 실행 될 때 배열"+selectedItemCntArr)
    } else {
        selectedItemCntArr = [];
        cartItemSelectElems.forEach((item) => {
            item.checked = true;
            // if(selectedItemCntArr.length !== cartItemSelectElems.length) {
            //     selectedItemCntArr.push(item);
            // }
            selectedItemCntArr.push(item);

            initAllPrice();
        });
        console.log("함수 실행 될 때 배열"+selectedItemCntArr)
        // document.querySelector('.total-price').innerText = numberWithCommas(saveTotalprice);
        cartItemSelectElems.forEach((item) => {
            item.addEventListener("click", () => {
                if (!selectedItemCntArr.includes(item)) {
                    if(item.checked === true) {
                        selectedItemCntArr.push(item);
                    }
                    // else {
                    //     selectedItemCntArr = selectedItemCntArr.filter((item) => item.checked === true);
                    // }
                } else {
                    selectedItemCntArr = selectedItemCntArr.filter((item) => item.checked === true);
                }
                console.log("개별 체크 눌렀을 때 배열 " + selectedItemCntArr)

                // let selectedItemCnt = selectedItemCntArr.filter((item) => item.checked !== false);
                let selectedItemPriceSum = 0;

                if (selectedItemCntArr == null) {
                    selectedItemPriceSum = 0;
                } else {
                    if (selectedItemCntArr.length === cartItemSelectElems.length) {
                        allSelectElem.checked = true;

                    } else {
                        allSelectElem.checked = false;
                    }
                    console.log(selectedItemCntArr);
                    selectedItemCntArr.forEach((item) => {
                        let selectedItemPrice = item.closest(".cart-item").querySelector(".cart-item-total-price").textContent.split(",").join("");
                        selectedItemPriceSum += parseInt(selectedItemPrice);
                    });
                    console.log(selectedItemPriceSum)
                }
                document.querySelector('.total-price').innerText = numberWithCommas(selectedItemPriceSum);
            })
        })
    }
}
cartItemsCheck();

allSelectElem.addEventListener("click", ()=> {
    cartItemsCheck();
})

//장바구니 총 합



let cartItemAllPriceValue = cartItemAllPrice.textContent.split(",").join("");

cartItemAllPrice.innerText = numberWithCommas(cartItemAllPriceValue);

//장바구니 안 상품 삭제

let cartItemDeleteElem = document.querySelectorAll(".cart-item-delete");
let iuser = document.querySelector("#loginUserPk").dataset.iuser;

initAllPrice();

cartItemDeleteElem.forEach((item)=> {
    item.addEventListener("click", ()=> {
        const cartItemId = item.closest(".cart-item").querySelector(".item_id").textContent;
        let cartItemTotalPrice = item.closest(".cart-item").querySelector(".cart-item-total-price").textContent.split(",").join("");
        const cartItemAllPrice = document.querySelector(".cart-item-all-price > span");
        console.log(cartItemAllPrice);
        const cartItemAllPriceValue = cartItemAllPrice.textContent.split(",").join("");

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
                item.closest('.cart-item').querySelector('.cart-item-check').checked = false;
                item.closest(".cart-item").remove();
                alert("상품을 장바구니에서 삭제하였습니다.");

                let price = 0;
                const checkArr = document.querySelectorAll(".cart-item-select > input");
                checkArr.forEach(item2 => {
                    const isChecked = item2.checked;
                    if(isChecked === true) {
                        price += parseInt(item2.closest(".cart-item").querySelector(".cart-item-total-price").innerText.split(',').join(''));
                    }
                });
                cartItemAllPrice.innerText = numberWithCommas(checkArr.length === 0 ? 0 : price);

                // cartItemAllPriceValue = parseInt(cartItemAllPriceValue) - parseInt(cartItemTotalPrice);
                selectedItemCntArr = selectedItemCntArr.filter((item) => item.checked !== false);
                if (selectedItemCntArr.length === checkArr.length) {
                    allSelectElem.checked = true;
                }
                console.log("삭제 했을 때 배열 "+ selectedItemCntArr)
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

                let price = 0;
                const checkArr = document.querySelectorAll(".cart-item-select > input");
                checkArr.forEach(item2 => {
                    const isChecked = item2.checked;
                    if(isChecked === true) {
                        price += parseInt(item2.closest(".cart-item").querySelector(".cart-item-total-price").innerText.split(',').join(''));
                    }
                });
                cartItemAllPrice.innerText = numberWithCommas(price);
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
                    cartItemCnt--;
                    item.closest(".cart-item-total").querySelector(".cart-item-cnt").innerText = cartItemCnt;
                    cartItemTotalPrice.innerText = numberWithCommas(cartItemPriceValue * cartItemCnt);

                    let price = 0;
                    const checkArr = document.querySelectorAll(".cart-item-select > input");
                    checkArr.forEach(item2 => {
                        const isChecked = item2.checked;
                        if(isChecked === true) {
                            price += parseInt(item2.closest(".cart-item").querySelector(".cart-item-total-price").innerText.split(',').join(''));
                        }
                    });
                    cartItemAllPrice.innerText = numberWithCommas(price);

                    // console.log(data);
                    // cartItemCnt--;
                    // item.closest(".cart-item-total").querySelector(".cart-item-cnt").innerText = cartItemCnt;
                    // cartItemTotalPrice.innerText = numberWithCommas(cartItemPriceValue * cartItemCnt);
                    // cartItemAllPrice.innerText = numberWithCommas(cartItemAllPriceValue - parseInt(cartItemPriceValue));
                    // cartItemAllPriceValue = cartItemAllPriceValue - parseInt(cartItemPriceValue);
                })
                .catch(e => {
                    console.error(e);
                });
        }


    })
})

//카카오페이로 선택 된 아이템 정보 보내기

let buyBtn = document.querySelector(".buy-btn");

buyBtn.addEventListener("click", (e)=> {
console.log(selectedItemCntArr[0].closest(".cart-item").querySelector(".cart-item-nm").textContent)

    let orderItemsId = []; //구매 기록 db 입력
    let orderItemsCnt = [];

    let totalCnt = 0;

    selectedItemCntArr.forEach((item)=> {
        orderItemsCnt.push(item.closest(".cart-item").querySelector(".cart-item-cnt").textContent);
        orderItemsId.push(item.closest(".cart-item").querySelector(".item_id").textContent);
        totalCnt += parseInt(item.closest(".cart-item").querySelector(".cart-item-cnt").textContent);
    })
    let orderItemsNm = selectedItemCntArr[0].closest(".cart-item").querySelector(".cart-item-nm").textContent + " 외 " + (totalCnt-1) + "개 상품"; //카카오페이 결제 사용

    let data = {
        "item_cnt" : orderItemsCnt,
        "item_id" : orderItemsId,
        "item_nm" : orderItemsNm,
        "quantity" : totalCnt,
        "total_amount" : document.querySelector(".total-price").textContent.split(",").join("")
    }
    console.log(data);
    fetch("/cart/orderInfo", {
            method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body: JSON.stringify(data)
        }).then(res=>res.json())
        .then(data => console.log(data))
        .catch((e)=> console.error(e))
})
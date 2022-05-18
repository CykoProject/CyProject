let allSelectElem = document.querySelector(".cart-items-select > input");

let cartItemSelectElems = document.querySelectorAll(".cart-item-select > input");

let selectedItemCntArr = []; // 9
function cartItemsCheck() {
    if (allSelectElem.checked === false) {
        cartItemSelectElems.forEach((item)=> {
            item.checked = false;
            selectedItemCntArr = [];
        })
    } else {
        cartItemSelectElems.forEach((item) => {
            item.checked = true;
            if(selectedItemCntArr.length !== cartItemSelectElems.length) {
                selectedItemCntArr.push(item);
            }
        })

        cartItemSelectElems.forEach((item) => {
            item.addEventListener("click", () => {
                let selectedItemCnt = selectedItemCntArr.filter((item) => item.checked !== false)

                if (selectedItemCnt.length === cartItemSelectElems.length) {
                    allSelectElem.checked = true;
                } else {
                    allSelectElem.checked = false;
                }
            })
        })

    }
}
cartItemsCheck();

// cartItemSelectElems.forEach((item)=> {
//     item.addEventListener("click", ()=> {
//         if(item.checked === true) {
//             selectedItemCntArr.push(item);
//         } else  {
//             selectedItemCntArr.filter((elem)=> elem.checked == true)
//         }
//         console.log(selectedItemCntArr.length)
//     })
//
// })

allSelectElem.addEventListener("click", ()=> {
    cartItemsCheck();
})


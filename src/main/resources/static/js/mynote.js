let mynote = document.querySelector('.my-note');
document.execCommand('defaultParagraphSeparator', false, 'p');
document.execCommand('fontSize', false, 4);

const mynoteObj = {
    width : '100%',
    height : '300px',
    font : [],
    select_font : '',
    pre_font : '',
    mod : '',
    setStyle : function (style) {
        document.execCommand(style);
    },
    createElem : function (type) {
        const result = document.createElement(type);
        switch (type) {
            case 'button' :
                result.type = type;
                result.style.padding = '0px 10px';
                result.addEventListener('click', () => {
                    if(result.style.background === 'gray') {
                        result.style.background = '';
                    } else {
                        result.style.background = 'gray';
                    }
                });
                break;
        }
        return result;
    },

    getFont : function () {
        fetch('/ajax/home/font?iuser=1')
            .then(res => res.json())
            .then(data => {
                this.setArea(data);
            })
            .catch(e => {
                console.error(e);
            });
    },
    setArea : function (data) {
        if(this.mod !== '') {
            mynote = this.mod;
        }
        mynote.style.display = 'none';
        const parent = mynote.parentNode;
        parent.style.width = this.width;

        //
        const tabDiv = document.createElement('div');
        tabDiv.style.display = 'flex';
        tabDiv.style.padding = '10px';
        tabDiv.style.background = '#f2f2f2';
        tabDiv.style.borderTop = '2px solid gray';

        const select = document.createElement('select');
        select.style.width = '100%';
        const option = document.createElement('option');
        option.innerText = '글꼴';
        select.appendChild(option);
        data.forEach(item => {
            const option = document.createElement('option');
            option.value = item.item_id.file;
            option.innerText = item.item_id.nm;
            select.appendChild(option);
        });
        select.addEventListener('change', () => {
            this.select_font = select[select.selectedIndex].value;
            document.execCommand('fontName', false, this.select_font);
        });
        tabDiv.appendChild(select);

        const sizeSelect = this.createElem('select');
        sizeSelect.style.width = '100%';
        sizeSelect.innerHTML = `
                <option value="">크기</option>
                <option value="1">10px</option>
                <option value="2">13px</option>
                <option value="3">16px</option>
                <option value="4">18px</option>
                <option value="5">24px</option>
                <option value="6">32px</option>
                <option value="7">48px</option>
            `;
        tabDiv.appendChild(sizeSelect);
        sizeSelect.addEventListener('change', (e) => {
            document.execCommand('fontSize', false, parseInt(e.target.value));
        });

        const boldBtn = this.createElem('button');
        boldBtn.classList.add('bold-btn');
        boldBtn.innerHTML = `<b>B</b>`;
        tabDiv.appendChild(boldBtn);
        boldBtn.addEventListener('click', () => {
            this.setStyle('bold');
        });

        const italicBtn = this.createElem('button');
        italicBtn.classList.add('italic-btn');
        italicBtn.innerHTML = `<i>I</i>`
        tabDiv.appendChild(italicBtn);
        italicBtn.addEventListener('click', () => {
            this.setStyle('italic');
        });

        const underLineBtn = this.createElem('button');
        underLineBtn.classList.add('under-line-btn');
        underLineBtn.innerHTML = `<u>U</u>`;
        tabDiv.appendChild(underLineBtn);
        underLineBtn.addEventListener('click', () => {
            this.setStyle('underline');
        });
        //

        parent.appendChild(tabDiv);

        const div = document.createElement('div');
        div.innerHTML = `
                <p>
                    <br>
                </p>
            `;
        div.classList.add('my-note-div')
        parent.appendChild(div);
        div.contentEditable = 'true';
        div.style.overflowY = 'scroll';
        div.style.background = 'white';
        div.style.border = '1px solid gray';
        div.style.padding = '0px 10px';
        div.style.wordBreak = 'break-word';
        div.style.height = mynoteObj.height;
        if(mynote.value.length > 0) {
            div.innerHTML = mynote.value;
        }
        div.focus({preventScroll: true});

        div.addEventListener('input', () => {
            mynote.value = div.innerHTML;
            const node = window.getSelection().anchorNode;
            const parentNode = node.parentNode;

            if(parentNode.classList.contains(this.pre_font)) {
                parentNode.classList.remove(this.pre_font);
            }
            parentNode.classList.add(this.select_font);

            this.pre_font = this.select_font;
        });
    }
}

mynoteObj.getFont();

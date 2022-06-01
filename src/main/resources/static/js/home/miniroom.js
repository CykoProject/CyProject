const roomSelectForm = document.querySelector('#room-select-form');

if (roomSelectForm) {
    const miniroomList = document.querySelectorAll('input[name="myroom"]');
    if (miniroomList) {
        miniroomList[0].checked = true;
    }

}
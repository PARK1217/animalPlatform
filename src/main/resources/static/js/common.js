

const phoneInput = document.getElementById('phone');

phoneInput.addEventListener('input', function(e) {
    let phoneNumber = e.target.value.replace(/[^0-9]/g, ''); // 숫자 이외의 문자 제거
    phoneNumber = phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'); // 하이픈 삽입

    e.target.value = phoneNumber;
});


function scrollToTop() {
    window.scrollTo(0, 0);
}

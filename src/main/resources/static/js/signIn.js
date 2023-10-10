// 로그인 상태 확인 변수 (예: true가 로그인된 상태, false가 로그아웃된 상태)
var isLoggedIn = true;

// 페이지 로드 시 실행되는 함수
window.onload = function() {
    // 로그인 상태에 따라 링크 표시/숨김 처리
    if (isLoggedIn) {
        document.getElementById("loginLink").style.display = "none";
        document.getElementById("logoutLink").style.display = "block";
    } else {
        document.getElementById("loginLink").style.display = "block";
        document.getElementById("logoutLink").style.display = "none";
    }
};
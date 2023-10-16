
function signInInit() {
    signIn();
}

function signIn() {
    var id = document.getElementById("id").value;
    var password = document.getElementById("password").value;

    if (id == "" || password == "") {
        alert("아이디 또는 비밀번호를 입력하세요.");
        return;
    }

    var data = {
        id: id,
        password: password
    };

    $.ajax({
        type: "POST",
        url: "/api/user/reg",
        contentType: "application/json",
        data: JSON.stringify(data),
        success: function (response) {
            if (response["result"] == "success") {
                alert("로그인에 성공하였습니다.");
                window.location.href = "/";
            } else {
                alert("로그인에 실패하였습니다.");
            }
        }
    });
}

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
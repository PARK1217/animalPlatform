
// 회원가입 정보 객체 생성
var signUpInfo = {
    userId: "",
    userPw: "",
    phone: "",
    email: ""
};

// 회원가입 버튼 클릭 시 Ajax 통신
document.getElementById("join").addEventListener("click", function() {
    // 입력된 값 가져오기
    signUpInfo.userId = document.getElementById("newId").value;
    signUpInfo.userPw = document.getElementById("newPw").value;
    signUpInfo.phone = document.getElementById("phone").value;
    signUpInfo.email = document.getElementById("email").value;

    // Ajax 요청 생성
    var xhr = new XMLHttpRequest();

    // 요청 완료 후 처리할 콜백 함수 등록
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // 성공적으로 응답 받았을 때의 동작 처리
                console.log(xhr.responseText);
            } else {
                // 오류 발생 시의 동작 처리
                console.log(xhr.responseText);
                console.error(xhr.status);
            }
        }
    };

    // POST 방식으로 서버에 데이터 전송하기 위한 설정
    xhr.open("POST", "/api/user/reg", true);

    // 서버로 전송할 데이터 설정 (예: JSON 형식)
    var data = JSON.stringify(signUpInfo);

    // Content-Type 설정 (JSON 형식일 경우)
    xhr.setRequestHeader('Content-Type', 'application/json');

    // 데이터 전송
    xhr.send(data);
});

//TODO : 아이디 중복 체크 ajax로 구현하기
function checkDuplicateId(id) {
    // 서버로 Ajax 요청을 보내어 중복 여부를 확인하는 로직을 작성해야 합니다.
    // 여기서는 간단한 예제로 중복 여부를 무작위로 설정하여 결과를 보여줍니다.

    // Ajax 요청 예시 (실제 서버와의 통신은 구현되어야 합니다.)
    const isDuplicate = Math.random() >= 0.5; // 무작위로 중복 여부 결정

    if (isDuplicate) {
        document.getElementById("duplicateMessage").textContent = "중복된 아이디입니다.";
    } else {
        document.getElementById("duplicateMessage").textContent = "";
    }
}

//TODO : 아이디 유효성 검사
function validateId(id) {
    // 여기서는 간단한 예제로 아이디가 영문 소문자로만 구성되어야 유효하다고 가정합니다.
    const isValid = /^[a-z]+$/.test(id);

    if (isValid) {
        document.getElementById("loginId").classList.remove("invalid-input");
        document.getElementById("loginId").classList.add("valid-input");
    } else {
        document.getElementById("loginId").classList.remove("valid-input");
        document.getElementById("loginId").classList.add("invalid-input");
    }
}
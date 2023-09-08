
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
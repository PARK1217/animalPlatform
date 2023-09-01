package com.example.animalplatform.define;

import com.example.animalplatform.log.JLog;

import java.util.Arrays;

public enum ReturnStatus {

    SUCCESS(0, "성공"),
    FAIL(4000, "실패"),
    FAIL_REQUIRED_LOGIN_USER(600, "로그인이 필요한 서비스 입니다."),

    FAIL_COMMON_ERROR(1000, "잘못된 요청입니다. 관리자에게 문의 주세요."),
    FAIL_NOT_EXIISTED_SESSION(1007, "세션이 존재하지 않습니다."),
    FAIL_INSUFFICIENT(1008, "권한이 부족합니다."),

    FAIL_NOT_LOGIN_STATUS(1009, "로그인 상태가 아닙니다."),
    FAIL_ATTEMPT_LOGIN(1010, "로그인을 실패했습니다."),

    FAIL_NO_CHANGE(1106, "변경 사항 없음"),
    FAIL_CUR_PASSWORD_IS_WRONG(1107, "현재 패스워드가 일치하지 않습니다."),

    FAIL_EXIISTED_USER(4101, "현재 사용중인 아이디 입니다. \n다른 아이디로 등록하세요."),
    FAIL_NOT_EXIISTED_USER(4102, "존재하는 않는 사용자입니다."),
    FAIL_NOT_EXIISTED_MAIL_NO(4104, "인증번호가 존재하지 않습니다."),
    FAIL_PASSWORD_IS_WRONG(4103, "패스워드 정보가 일치하지 않습니다."),
    FAIL_EXIISTED_BRANCH(4108, "이미 등록된 지점입니다."),

    FAIL_NOT_EXISTED_DATA(4201, "데이터가 존재하지 않습니다."),
    FAIL_NOT_EXISTED_UPLOAD_FILE(4202, "업로드할 파일이 존재하지 않습니다."),
    FAIL_NOT_MATCH_NAME(4212, "사용자 이름과 일치하지 않습니다."),
    FAIL_NOT_MATCH_USER(4215, "권한이 없습니다."),
    FAIL_TO_SEND_MAIL(4211, "매일 전송 중 오류가 발생했습니다."),
    FAIL_FORBIDDEN_EXT(4212, "업로드 금지된 파일 형식입니다."),
    FAIL_INVALID_PARAMETERS(4213, "잘못된 매개변수입니다."),
    FAIL_NOT_EXISTED_PARENT_DATA(4214, "상위 데이터가 존재하지 않습니다."),
    FAIL_EXISTED_CHILD_DATA(4216, "하위 데이터가 존재합니다.\n하위 테이터를 삭제 후 다시 시도해 주세요."),
    FAIL_RETRY_LOGIN(4217, "세션이 만료됐습니다."),
    FAIL_USER_WITHDRAW(4228, "탈퇴된 회원입니다."),
    FAIL_EXIISTED_EMAIL(4229, "존재하는 이메일 주소입니다."),
    FAIL_NOT_EXIST_FILE(4230, "파일이 존재 하지 않습니다."),
    FAIL_EMAIL_IS_EMPTY(4231, "이메일 정보가 없습니다."),
    FAIL_PASSWORD_IS_EMPTY(4232, "패스워드가 빈값입니다."),
    FAIL_NAME_IS_EMPTY(4233, "이름을 입력해주세요."),
    FAIL_PHONE_IS_EMPTY(4234, "전화번호가 누락됐습니다."),
    FAIL_BIRTH_IS_EMPTY(4235, "생년월일을 입력해주세요."),
    FAIL_MAIL_NO_IS_EMPTY(4236, "메일인증번호를 입력해주세요."),

    FAIL_CUR_PASSWORD_IS_SAME(1107, "현재 패스워드와 동일합니다."),
    FAIL_CHECK_PW_1(4246, "패스워드는 \' 포함할 수 없습니다."),
    FAIL_CHECK_PW_2(4246, "패스워드는 \" 포함할 수 없습니다."),
    FAIL_CHECK_PW_3(4246, "패스워드는 \\ 포함할 수 없습니다."),
    FAIL_CHECK_PW_4(4246, "패스워드는 | 포함할 수 없습니다."),
    FAIL_CHECK_PW_5(4246, "패스워드는 null은 입력할 수 없습니다."),
    FAIL_CHECK_PW_6(4246, "패스워드는 8자리 이상 입력해주세요."),
    FAIL_CHECK_PW_7(4246, "패스워드는 영문+숫자+특수문자 조합으로 입력해주세요."),
    FAIL_CHECK_PW_8(4246, "패스워드는 중복된 4자 이상의 문자 사용불가"),
    FAIL_CHECK_PW_9(4246, "패스워드는 키보드 배열 연속 4자 이상의 문자 사용불가"),
    FAIL_CHECK_PW_10(4246, "패스워드는 연속 4자 이상의 문자 사용불가"),
    FAIL_CHECK_PW_11(4246, "전화번호는 포함할 수 없습니다."),
    FAIL_PASSWORD_IS_NOT_GOOD(4246, "비밀번호로 적합하지 않습니다."),

    FAIL_EMAIL_IS_ALREADY_EXIST(4252, "이미 등록된 이메일입니다."),
    FAIL_MAIL_NO_IS_EXPIRED(4254, "인증번호 입력시간이 만료됐습니다."),
    FAIL_MAIL_NO_IS_NOT_MATCH(4255, "인증번호가 일치하지 않습니다."),
    FAIL_EXISTED(4256, "이미 등록된 데이터가 존재합니다."),
    FAIL_EXCEED_FILE(4257, "파일 크기가 업로드 파일 크기 제한을 초과했습니다."),
    FAIL_EXIST_NOT_ALLOW_FILE(4258, "허가되지 않은 파일 확장자입니다."),
    FAIL_MORE_DUPLICATE_DATA_HAVE_BEEN_ENTERED(4259, "중복된 데이터가 입력되었습니다."),
    FAIL_END_DATE_IS_BEFORE_START_DATE(4260, "종료일이 시작일보다 이전입니다."),
    FAIL_DATE_FORMAT(4261, "날짜 형식이 잘못되었습니다."),

    FAIL_NOT_FOUND_SELF_USER(5101, "찾을 수 없는 사용자입니다."),
    FAIL_NOT_REGISTER_FAMILY(5102, "등록되어 있지 않는 가족입니다."),

    FAIL_EXISTED_TPL_NAME(5201, "이미 등록된 템플릿 이름입니다."),
    FAIL_NOT_FIND_TPL(5202, "찾을 수 없는 템플릿입니다."),

    FAIL_NOT_FOUND_HOSPITAL(6101, "찾을 수 없는 병원입니다."),

    FAIL_NOT_FOUND_TREATMENT(7101, "찾을 수 없는 진료내역입니다."),
    FAIL_EXIST_TREATMENT(7102, "이미 진료예약이 존재하는 날짜입니다."),
    FAIL_NOT_FOUND_IN_PROGRESS_TREATMENT(7103, "대기중이거나 진행중인 진료 내역이 아닙니다."),
    FAIL_NOT_CANCELED_STATUS_TREATMENT(7104, "취소상태의 진료내역이 아닙니다."),
    FAIL_NOT_RESERVED_MY_TREATMENT(7105, "사용자의 진료내역이 아닙니다."),
    FAIL_NOT_FOUND_DISEASE_CODENAME(7201, "증상명칭을 찾을 수 없는 증상코드입니다."),

    FAIL_NOT_FOUND_AREA_NO(7301, "해당 지점코드를 찾을 수 없습니다."),

    FAIL_NOT_CONDITIONS(8101, "조건이 충족되지 않습니다."),

    FAIL_DISAGREE_PUSH_USER(8102, "push 수신거부 사용자입니다."),
    FAIL_NOT_SEND_PUSH(8103, "푸시 전송에 실패했습니다."),
    FAIL_NOT_WITHDRAW_TREAT(8104,"진행 중인 대면 진료 예약이 존재합니다." ),

    FAIL_NOT_WITHDRAW_ALLACARE(8105,"올라케어와 통신 중 오류가 발생했습니다." ),
    FAIL_CONNECT_OCARE(8106, "오케어 연동에 실패했습니다."),

    FAIL_NOT_FOUND_TREATMENT_LIMIT(8107, "진료/건건검진상담 예약 간 비율 설정이 되지 않았습니다."),
    FAIL_SIGN_UP_BY_OCARE(8108, "이미 존재하는 사번을 가진 사용자입니다."),
    FAIL_EMPTY_COMPID_USER(8109, "대상자가 아닙니다."),
    FAIL_NOT_FOUND_CUSTOMER(8110, "계열사 정보를 찾을 수 없습니다."),
    FAIL_NOT_REGISTER_CUSTOMER_CODE(8111, "등록되지 고객사 코드 입니다."),

    FAIL_NOT_REGISTERED_HEALTH_CHECK_USER(9101, "등록되지 않았거나 관련되지 않는 건강검진 대상자입니다."),
    FAIL_ALREADY_REGISTERED_HEALTH_CHECK_USER(9102, "이미 건강검진 예약을 한 사용자입니다."),

    FAIL_IS_HOLIDAY(9202, "공휴일 입니다."),
    FAIL_IS_HOLIDAY_OR_WEEKEND(9202, "주말 또는 병원 지정 휴일입니다."),
    FAIL_INVALID_RATIO(9203, "관리 비율이 잘못되었습니다."),
    FAIL_EXCEED_TO_RESERVATION_TREATMENT_RATIO(9204, "진료 예약 비율이 초과되었습니다."),
    FAIL_EXCEED_TO_RESERVATION_COUNSELING_RATIO(9205, "건강검진 상담 예약 비율이 초과되었습니다."),

    FAIL_CALL_BIZ_CARE_API(10001, "요청에 실패했습니다. 관리자에게 문의 주세요."),
    FAIL_NOT_UNIQUE_DATA(10001, "동일한 정보가 있어 요청에 실패했습니다."),
    FAIL_HEALTH_CHECK_RESERVATION(10002, "건강 검진 예약에 실패했습니다."),
    FAIL_MODIFY_HEALTH_CHECK_USER_INFO(10003, "사용자 정보 변경에 실패했습니다."),
    FAIL_HEALTH_CHECK_CANCEL(10004, "건강 검진 예약취소에 실패했습니다."),
    FAIL_HEALTH_CHECK_MODIFY(10004, "건강 검진 예약 변경에 실패했습니다."),
    FAIL_HEALTH_CHECK_FAMILY_REMOVE(10004, "예약정보가 있거나, 관련없는 가족 구성원입니다."),
    FAIL_IS_NOT_HEALTH_CHECK_FAMILY(10005, "본인 부담 가족 구성원이 아닙니다."),

    FAIL_NOT_SMS_SEND_INFO_SAVE(11001, "SMS 전송 정보 저장에 실패했습니다."),
    FAIL_ALREADY_EXISTED_SMS_INFO(11002, "이미 등록된 SMS 정보입니다."),

    FAIL_NOT_FOUND_TERMS_INFO(12000, "약관정보가 존재하지 않습니다."),
    FAIL_NEED_TO_ACCESS_TOKEN(13000, "인증 토큰이 필요합니다."),
    FAIL_INVALID_ACCESS_TOKEN(13001, "유효하지 않은 인증 토큰 입니다."),
    FAIL_IS_EXPIRED_ACCESS_TOKEN(13002, "만료된 인증 토큰 입니다."),
    FAIL_NEED_TO_SECRET_KEY(13003, "Secret Key는 필수입니다."),
    FAIL_INVALID_SECRET_KEY(13004, "유효하지 않은 Secret Key 입니다."),
    ;

    private Integer code;
    private String msg;

    ReturnStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * printDebugMessage()
     * 예외 메세지를 전달할 때 사용한다.
     */
    public void printErrorMessage() {
        JLog.loge(String.format("%s", this.msg));
    }

    public void printErrorMessage(Object... obj) {
        JLog.loge(String.format("%s : %s", this.msg, Arrays.toString(obj)));
    }

}
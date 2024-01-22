package map.homepage.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import map.homepage.apiPayload.code.BaseCode;
import map.homepage.apiPayload.code.ReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 유저 관련 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "해당 사용자가 존재하지 않습니다."),
    // 로그인 응답
    NEED_USER_DETAIL(HttpStatus.OK, "LOGIN200", "토큰이 유효하고 유저의 추가정보가 필요합니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


    @Override
    public ReasonDTO getReasonHttpStatus(){
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}

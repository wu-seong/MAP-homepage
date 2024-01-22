package map.homepage.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import map.homepage.apiPayload.code.BaseCode;
import map.homepage.apiPayload.code.ReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

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

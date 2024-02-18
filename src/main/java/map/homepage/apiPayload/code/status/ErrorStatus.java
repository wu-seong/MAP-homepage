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

    // 댓글 관련 응답
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT4001", "해당 댓글 id가 존재하지 않습니다."),
    COMMENT_IS_EMPTY(HttpStatus.BAD_REQUEST, "COMMENT4002", "댓글은 null일 수 없습니다."),
    COMMENT_IS_BLANK(HttpStatus.BAD_REQUEST, "COMMENT4003", "댓글은 공백일 수 없습니다."),
    COMMENT_TOO_LONG(HttpStatus.BAD_REQUEST, "COMMENT4004", "해당 댓글의 길이가 100을 넘어갑니다.");
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

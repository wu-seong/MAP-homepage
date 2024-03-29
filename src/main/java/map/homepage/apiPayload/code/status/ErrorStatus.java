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
    USER_PROFILE_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4002", "해당 사용자의 프로필 이미지가 존재하지 않습니다."),
    // 게시글 관련 응답
    ARTICLE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4001", "게시글이 존재하지 않습니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "ARTICLE4002", "첨부된 사진이 없습니다."),
    IS_NOT_IMAGE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "ARTICLE4003", "첨부된 파일이 사진 형태가 아닙니다."),
    MINIMUM_IMAGE_REQUIREMENT_NOT_MET(HttpStatus.BAD_REQUEST, "ARTICLE4004", "이미지 개수는 최소한 1개 이상이어야 합니다."),

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

package map.homepage.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.enums.SocialType;
import map.homepage.domain.member.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MemberResponseDTO {

    // 첫 로그인 응답
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO{
        private Long id;
        private boolean infoSet;
        private LocalDateTime createdAt;
    }

    // 멤버 기본 정보
    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberPreviewDTO{
        private String studentId;
        private String name;
        private String nickname;
        private String grade;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class MemberPreviewListDTO{
        private List<MemberPreviewDTO> memberPreviewDTOList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    // 멤버 세부 정보
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDetailDTO{
        private Long id;
        private Long oauthId;
        private String studentId;
        private boolean infoSet;
        private String name;
        private String grade;
        private String email;
        private Status status;
        private LocalDateTime inactiveDate;
        private String nickname;
        private LocalDate birth;
       // private String imageUri; //프로필 이미지 경로
        private SocialType socialType; //oauth2 제공자
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Role role;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class MemberDetailListDTO{
        private List<MemberDetailDTO> memberDetailDTOList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    // ---------- 아래는 스웨거 응답에 제네릭 타입 적용을 위한 클래스 정의 ----------
    public static class ApiResponseMemberDetailListDTO extends ApiResponse<MemberDetailListDTO>{
        public ApiResponseMemberDetailListDTO(Boolean isSuccess, String code, String message, MemberDetailListDTO result) {
            super(isSuccess, code, message, result);
        }
    }
    public static class ApiResponseMemberPreviewListDTO extends  ApiResponse<MemberPreviewListDTO>{
        public ApiResponseMemberPreviewListDTO(Boolean isSuccess, String code, String message, MemberPreviewListDTO result) {
            super(isSuccess, code, message, result);
        }
    }
}
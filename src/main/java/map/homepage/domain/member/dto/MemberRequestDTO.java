package map.homepage.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 Member 관련 DTO 클래스 정의
 */
public class MemberRequestDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    public static class MemberSignUpDTO{
        private String studentId;
        private String nickname;
        private String birth;
        private String grade;
    }

}

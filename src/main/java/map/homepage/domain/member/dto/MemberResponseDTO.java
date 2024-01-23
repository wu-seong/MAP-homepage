package map.homepage.domain.member.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.enums.SocialType;
import map.homepage.domain.member.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTO{
        private Long id;
        private boolean infoSet;
        private LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MemberDetailDTO{
        private Long id;
        private Long oauthId;
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
}
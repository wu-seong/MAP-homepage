package map.homepage.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.member.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class MemberResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberDetailDTO{
        private Long id;

        private String oauthId;

        private String studentId;

        private String name;

        private String grade;

        private String email; // oauth2 제공자가 알려준 email

        private Status status;

        private LocalDateTime inactiveDate;

        private String nickname;

        private LocalDate birth;

        private String imageUri; //프로필 이미지 경로

        private String provider; //oauth2 제공자
    }
}

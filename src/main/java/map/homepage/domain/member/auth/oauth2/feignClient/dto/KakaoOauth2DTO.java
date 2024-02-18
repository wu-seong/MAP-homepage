package map.homepage.domain.member.auth.oauth2.feignClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import map.homepage.domain.member.enums.SocialType;

import java.time.LocalDateTime;

public class KakaoOauth2DTO {

    @Getter
    public static class TokenResponseDTO{
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
    }

    @Getter
    @ToString
    public static class UserInfoResponseDTO{
        private String id;
        @JsonProperty("connected_at")
        private LocalDateTime connectedAt;
        @JsonProperty("kakao_account")
        private KakaoAccountDTO kakaoAccount;
        private SocialType socialType;
    }
    @Getter
    @ToString
    public static class KakaoAccountDTO{
        // 프로필 동의항목
        private Profile profile;
        // 이메일 동의항목
        private String email;

        // 이름 동의 항목
        private String name;
    }

    @Getter
    @ToString
    public static class Profile{
        @JsonProperty("profile_image_url")
        private String imageUri;
    }
}
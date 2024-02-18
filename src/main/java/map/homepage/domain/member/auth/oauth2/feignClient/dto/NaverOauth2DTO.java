package map.homepage.domain.member.auth.oauth2.feignClient.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import map.homepage.domain.member.enums.SocialType;

public class NaverOauth2DTO {

    @Getter
    public static class TokenResponseDTO{
        @JsonProperty("token_type")
        private String tokenType;
        @JsonProperty("access_token")
        private String accessToken;
        @JsonProperty("refresh_token")
        private String refreshToken;

    }
    @Getter
    @ToString
    public static class UserInfoResponseDTO{
        @JsonProperty("resultcode")
        private String resultCode;
        private String message;
        @JsonProperty("response")
        private NaverAccountDTO naverAccount;
        private SocialType socialType;
    }
    @Getter
    @ToString
    public static class NaverAccountDTO{
        private String id;
        private String name;
        private String email;
        @JsonProperty("profile_image")
        private String imageUri;
    }


}

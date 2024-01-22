package map.homepage.domain.member.auth.oauth2.feignClient.client;

import feign.Headers;
import map.homepage.domain.member.auth.oauth2.feignClient.config.FeignConfig;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-oauth2-client", url = "https://kauth.kakao.com", configuration = FeignConfig.class)
public interface KakaoOauth2Client {
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @PostMapping("/oauth/token")
    KakaoOauth2DTO.TokenResponseDTO getAccessToken(@RequestParam(name = "grant_type") String grantType,
                                                   @RequestParam(name = "client_id") String clientId,
                                                   @RequestParam(name = "redirect_uri") String redirectUri,
                                                   @RequestParam(name = "code") String code
    );

}
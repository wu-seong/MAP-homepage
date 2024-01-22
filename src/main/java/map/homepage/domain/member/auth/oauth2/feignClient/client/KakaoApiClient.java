package map.homepage.domain.member.auth.oauth2.feignClient.client;


import feign.Headers;
import map.homepage.domain.member.auth.oauth2.feignClient.config.FeignConfig;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-api-client", url = "https://kapi.kakao.com", configuration = FeignConfig.class)
public interface KakaoApiClient {
    @PostMapping(value = "/v2/user/me")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken, @RequestParam(name = "property_keys") String propertyKeys);

}

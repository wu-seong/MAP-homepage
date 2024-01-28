package map.homepage.domain.member.auth.oauth2.feignClient.client;


import map.homepage.domain.member.auth.oauth2.feignClient.config.FeignConfig;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.NaverOauth2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naver-api-client", url = "https://openapi.naver.com", configuration = FeignConfig.class)
public interface NaverApiClient {
    @GetMapping(value = "/v1/nid/me")
    NaverOauth2DTO.UserInfoResponseDTO getUserInfo(@RequestHeader("Authorization") String accessToken);
}

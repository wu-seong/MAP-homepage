package map.homepage.domain.member.auth.oauth2.feignClient.client;


import map.homepage.domain.member.auth.oauth2.feignClient.config.FeignConfig;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.NaverOauth2DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-oauth2-client", url = "https://nid.naver.com/oauth2.0", configuration = FeignConfig.class)
public interface NaverOauth2Client {

    @GetMapping(value = "/token")
    NaverOauth2DTO.TokenResponseDTO getToken(@RequestParam(name = "grant_type") String grantType,
                                             @RequestParam(name = "client_id") String clientId,
                                             @RequestParam(name = "client_secret") String clientSecret,
                                             @RequestParam(name = "redirect_uri") String redirectUri,
                                             @RequestParam(name = "code") String code);
}

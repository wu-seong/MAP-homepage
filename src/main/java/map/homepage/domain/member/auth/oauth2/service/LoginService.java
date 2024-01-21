package map.homepage.domain.member.auth.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.auth.oauth2.feignClient.client.KakaoApiClient;
import map.homepage.domain.member.auth.oauth2.feignClient.client.KakaoOauth2Client;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final KakaoOauth2Client kakaoOauth2Client;
    private final KakaoApiClient kakaoApiClient;

    /*
        테스트 용으로 만든거, 실제로는 프론트에서 처리해서 액세스 토큰만 가져다 줌
    */
    public String getAccessToken(String code){
        KakaoOauth2DTO.TokenResponseDTO tokenResponseDTO = kakaoOauth2Client.getAccessToken("authorization_code",
                "a646059593978bf76530118502f575f3",
                "http://localhost:3000/oauth2/login/kakao",
                code);
        log.info("token = {}", tokenResponseDTO.getAccessToken());
        return tokenResponseDTO.getAccessToken();
    }

    /*
       토큰으로 유저정보를 가져오는 메서드
    */
    public KakaoOauth2DTO.UserInfoResponseDTO getUserInfo(String accessToken) throws JsonProcessingException {
        String emailProperty = "kakao_account.email";
        String nameProperty = "kakao_account.name";
        List<String> propertyKeysList = Arrays.asList(emailProperty, nameProperty);

        // List -> JSON 형식으로 바꾸어 전달
        ObjectMapper objectMapper = new ObjectMapper();
        String propertyKeys = objectMapper.writeValueAsString(propertyKeysList);
        KakaoOauth2DTO.UserInfoResponseDTO userInfoResponseDTO = kakaoApiClient.getUserInfo(accessToken, propertyKeys);
        log.info("userInfo ={}", userInfoResponseDTO.getKakaoAccount());
        return userInfoResponseDTO;
    }
}
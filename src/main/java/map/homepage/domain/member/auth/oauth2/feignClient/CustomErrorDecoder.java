package map.homepage.domain.member.auth.oauth2.feignClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.ErrorResponseDTO;
import map.homepage.exception.GeneralException;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private static final String tokenValidatePath = "https://kapi.kakao.com/v1/user/access_token_info";
    @Override
    public Exception decode(String methodKey, Response response) {
        // 응답 본문 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorResponseDTO errorResponseDTO = null;
        try {
            errorResponseDTO = objectMapper.readValue(response.body().asInputStream(), ErrorResponseDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int code = errorResponseDTO.getCode();

        String path = response.request().url();
        // 토큰 유효성 응답에 대한 처리
        if(StringUtils.endsWithIgnoreCase(path, tokenValidatePath)){

        }
        return new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}
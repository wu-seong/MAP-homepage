package map.homepage.domain.member.auth.oauth2.feignClient.config;


import feign.codec.ErrorDecoder;
import map.homepage.domain.member.auth.oauth2.feignClient.CustomErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    public ErrorDecoder errorDecode(){
        return new CustomErrorDecoder();
    }
}

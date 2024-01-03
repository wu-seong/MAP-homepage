package map.homepage.auth.oauth2.config;

import lombok.RequiredArgsConstructor;
import map.homepage.auth.oauth2.CustomOAuth2UserService;
import map.homepage.auth.oauth2.handler.OAuth2FailureHandler;
import map.homepage.auth.oauth2.handler.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security 설정을 활성화시켜주는 어노테이션
@RequiredArgsConstructor
public class OAuth2ClientSecurityConfig {
    private final CustomOAuth2UserService customOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login( oAuth2LoginConfigurer -> {
                            oAuth2LoginConfigurer
                            .authorizationEndpoint( authorizationEndpointConfig -> {
                                authorizationEndpointConfig.baseUri("/oauth2/authorize");
                            })
                            .redirectionEndpoint( redirectionEndpointConfig -> {
                                redirectionEndpointConfig.baseUri("/login/oauth2/code/**");
                            })
//                            .tokenEndpoint( token ->{
//                                token.accessTokenResponseClient(DefaultAuthorizationCodeTokenResponseClient);
//                            })
                            .userInfoEndpoint( userInfo ->{
                                userInfo.userService(customOauth2UserService);
                            })
                            .successHandler(oAuth2SuccessHandler)
                            .failureHandler(oAuth2FailureHandler);
                        }
                );


        return http.build();
    }
}


package map.homepage.domain.member.auth.oauth2.config;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.auth.jwt.filter.JwtAuthFilter;
import map.homepage.domain.member.auth.jwt.service.JwtTokenProvider;
import map.homepage.domain.member.auth.oauth2.CustomOAuth2UserService;
import map.homepage.domain.member.auth.oauth2.handler.OAuth2FailureHandler;
import map.homepage.domain.member.auth.oauth2.handler.OAuth2SuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // spring security 설정을 활성화시켜주는 어노테이션
@RequiredArgsConstructor
public class OAuth2ClientSecurityConfig {
    private final CustomOAuth2UserService customOauth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf( csrf -> {
                  csrf.disable();
                })
                .formLogin( form ->{
                    form.disable();
                })
                .httpBasic( basic ->{
                    basic.disable();
                })
                .logout( out ->{
                    out.disable();
                })
                // 세션기반 인증이 아니니 사용하지 않도록 설정
                .sessionManagement( session ->{
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authorizeHttpRequests( authz ->{
                    authz.requestMatchers("test").authenticated();
                })
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), OAuth2LoginAuthenticationFilter.class)
                .oauth2Login( oAuth2LoginConfigurer -> {
                            oAuth2LoginConfigurer
                            // 로그인 요청 url 설정
                            .authorizationEndpoint( authorizationEndpointConfig -> {
                                authorizationEndpointConfig.baseUri("/oauth2/authorize");
                            })
                            // 인증코드를 전달받을 url 설정
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
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
       return (web)-> web.ignoring().requestMatchers("/v3/**", "/swagger-ui/**");
    }
}


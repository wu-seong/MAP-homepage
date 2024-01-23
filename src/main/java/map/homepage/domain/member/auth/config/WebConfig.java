package map.homepage.domain.member.auth.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.auth.jwt.filter.CorsFilter;
import map.homepage.domain.member.auth.jwt.filter.JwtAuthFilter;
import map.homepage.domain.member.auth.jwt.filter.MemberInterceptor;
import map.homepage.domain.member.auth.jwt.service.JwtTokenProvider;
import map.homepage.domain.member.auth.jwt.service.JwtUtil;
import map.homepage.domain.member.service.MemberService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public FilterRegistrationBean<JwtAuthFilter> jwtFilterRegistration() {
        FilterRegistrationBean<JwtAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtAuthFilter(jwtTokenProvider)); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*");
        registration.setOrder(2); // 필터의 순서 설정. 값이 낮을수록 먼저 실행
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CorsFilter()); // 필터 인스턴스 설정
        registration.addUrlPatterns("/*");
        registration.setOrder(1); // 필터의 순서 설정. 값이 낮을수록 먼저 실행
        return registration;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor = new OpenEntityManagerInViewInterceptor();
        openEntityManagerInViewInterceptor.setEntityManagerFactory(entityManagerFactory);
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor);

        registry.addInterceptor(new MemberInterceptor(memberService, jwtUtil))
                .addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui/**", "/v3/api-docs/**", "/oauth2/**", "/post/**");

    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://localhost:3000")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .exposedHeaders("Access-Token", "Refresh-Token");
//    }
}
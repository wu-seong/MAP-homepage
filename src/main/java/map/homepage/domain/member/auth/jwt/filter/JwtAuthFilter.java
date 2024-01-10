package map.homepage.domain.member.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.auth.jwt.service.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
header에서 액세스 토큰 가져오기
permitAll이면 다음 필터로 넘기기(dofilter)
토큰의 유효성을 검증(만료시간 등)
토큰의 payload에 있는 값으로 인증절차 거치기(email등)
유저정보를 포함한 인증객체 만들어 SecurityContext로 등록 (jwtauthenticationToken)
 */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // permitAll()이 걸려있는 URI는 필터 건너뛰기
        log.info("url = {}", request.getRequestURL().toString());
        if ( request.getRequestURL().toString().startsWith("/oauth2/authorize") ) {
            log.info("로그인 요청은 통과");
            doFilter(request, response, filterChain);
        }
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        // AccessToken 추출하여 검증
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        if (jwtTokenProvider.verifyToken(accessToken)){
            setAuthentication(accessToken);
            log.info("인증성공");
            filterChain.doFilter(request, response);
        }
        else{
            log.info("인증실패");
            filterChain.doFilter(request, response);
        }
    }
    // SecurityContext 에 Authentication 객체를 저장합니다.
    public void setAuthentication(String token) {
        // 토큰으로부터 유저 정보를 받아옵니다.
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        // SecurityContext 에 Authentication 객체를 저장합니다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        log.info("저장된 인증객체 = {}", authentication1);
    }
}

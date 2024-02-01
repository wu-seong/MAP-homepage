package map.homepage.domain.member.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.auth.jwt.service.JwtTokenProvider;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
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
    private final static String[] ignorePrefix = {"/swagger-ui", "/v3/api-docs", "/oauth2", "/health"};
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 지정된 Path는 건너뛰기
        String currentPath = request.getServletPath();
        for(String ignorePath: ignorePrefix){
            if ( currentPath.startsWith(ignorePath) ) {
                doFilter(request, response, filterChain);
                return;
            }
        }
        String accessToken = null;
        String authHeader = request.getHeader("Authorization");
        // AccessToken 추출하여 검증
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            accessToken = authHeader.substring(7);
        }
        if (jwtTokenProvider.verifyToken(accessToken)){
            log.info("인증성공");
            filterChain.doFilter(request, response);
        }
        else{
            log.info("인증실패");
            response.sendError(401, "Unauthorized");
        }
    }
}

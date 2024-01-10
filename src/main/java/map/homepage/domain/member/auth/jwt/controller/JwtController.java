package map.homepage.domain.member.auth.jwt.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.auth.jwt.service.JwtTokenProvider;
import map.homepage.domain.member.auth.jwt.service.JwtUtil;
import map.homepage.domain.member.auth.jwt.token.JwtToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtUtil jwtUtil;

    //리프레시 토큰으로 액세스 토큰 재발급 메서드
    @PostMapping("/auth/jwt/access-token")
    public ResponseEntity recreate(@RequestHeader("Authorization") String authHeader){
        String refreshToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            refreshToken = authHeader.substring(7);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
        if( jwtTokenProvider.verifyToken(refreshToken) ){
            String email = jwtTokenProvider.getEmail(refreshToken);
            String role = jwtTokenProvider.getRole(refreshToken);
            JwtToken jwtToken = jwtUtil.generateToken(email, role);
            log.info("accessToken = {}", jwtToken.getAccessToken());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Access-Token", jwtToken.getAccessToken());
            headers.add("Refresh-Token", jwtToken.getRefreshToken());
            return ResponseEntity.ok().headers(headers).body("Hello, World!");
        }
        return ResponseEntity.badRequest().build();
    }

}

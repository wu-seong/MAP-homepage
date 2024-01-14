package map.homepage.domain.member.auth.jwt.service;

import com.nimbusds.oauth2.sdk.auth.JWTAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/*
토큰 인증을 처리하는 서비스
jjwt 라이브러리를 이용하여 signature를 비교함과 동시에 parse를 하고
parse하여 얻은 정보를 통해 유저를식별하고 filter에 넘겨줄 인증 객체를 만든다.(JwtAuthenticationToken)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${spring.jwt.secret}")
    private String secret;
    private SecretKey secretkey;
    private NimbusJwtDecoder jwtDecoder;
    @PostConstruct
    public void init() {
        this.secretkey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretkey).build();
    }

    public boolean verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretkey)
                    .build() // 비밀키를 설정하여 파서를 빌드.
                    .parseSignedClaims(token);  // 주어진 토큰을 파싱하여 Claims 객체를 얻는다.
            // 토큰의 만료 시간과 현재 시간비교
            return claims.getBody()
                    .getExpiration()
                    .after(new Date());  // 만료 시간이 현재 시간 이후인지 확인하여 유효성 검사 결과를 반환
        } catch (Exception e) {
            log.info("토큰 만료 = {}", token);
            return false;
        }
    }


    //accessToken decode하여 인증객체 생성
    public Authentication getAuthentication(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(jwt);
        jwtAuthenticationToken.setAuthenticated(true);

        return jwtAuthenticationToken;
    }
}

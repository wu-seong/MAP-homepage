package map.homepage.domain.member.auth.jwt.token;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}

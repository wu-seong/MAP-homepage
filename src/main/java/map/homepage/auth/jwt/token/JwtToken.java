package map.homepage.auth.jwt.token;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JwtToken {
    private String accessToken;
    private String refreshToken;
}

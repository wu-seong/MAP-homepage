package map.homepage.auth.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import map.homepage.domain.user.Role;
import map.homepage.domain.user.User;

import java.util.Map;
@Builder
@AllArgsConstructor
@Getter
public class OAuthAttributes {
    private Map<String,Object> attributes;
    private String provider;
    private String userPK;
    private String name;
    private String email;
    private String imageUri;

    //사용자 정보는 Map이기 때문에 변경해야함
    public static OAuthAttributes of(String provider, String userPK, Map<String,Object> attributes){
        //네이버 로그인 인지 판단.
        if("naver".equals(provider)){
            return ofNaver("id",attributes, provider);
        }
        return ofGoogle(userPK, attributes, provider);
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                //.picture(picture)
                .role(Role.USER)
                .build();
    }
    private static OAuthAttributes ofNaver(String userPK, Map<String, Object> attributes, String provider) {
        // 응답 받은 사용자의 정보를 Map형태로 변경.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        // 미리 정의한 속성으로 빌드.
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
//                .picture((String) response.get("profile_image"))
                .provider(provider)
                .attributes(response)
                .userPK(userPK)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userPK, Map<String,Object> attributes, String provider){
        // 미리 정의한 속성으로 빌드.
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
                .provider(provider)
                .attributes(attributes)
                .userPK(userPK)
                .build();
    }
}

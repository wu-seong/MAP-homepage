package map.homepage.domain.member.auth.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.Member;

import java.util.Map;
@Builder
@AllArgsConstructor
@Getter
public class OAuthAttributes {
    //private Map<String,Object> attributes;
    private String provider;
    private Long oauthId;
    private String name;
    private String email;
    private String imageUri;

    //사용자 정보는 Map이기 때문에 변경해야함
    public static OAuthAttributes of(String provider, Map<String,Object> attributes){
        //네이버 로그인 인지 판단.
        if("naver".equals(provider)){
            return ofNaver(attributes, provider);
        }
        return ofGoogle(attributes, provider);
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .oauthId(oauthId)
                .imageUri(imageUri)
                .role(Role.USER)
                .build();
    }
    private static OAuthAttributes ofNaver(Map<String, Object> attributes, String provider) {
        // 응답 받은 사용자의 정보를 Map형태로 변경.
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        // 미리 정의한 속성으로 빌드.
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .imageUri((String) response.get("profile_image"))
                .oauthId((Long) response.get(("id")))
                .provider(provider)
                //.attributes(response)
                .build();
    }

    private static OAuthAttributes ofGoogle(Map<String,Object> attributes, String provider){
        // 미리 정의한 속성으로 빌드.
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .imageUri((String) attributes.get("picture"))
                .oauthId((Long) attributes.get(("sub")))
                .provider(provider)
                //.attributes(attributes)
                .build();
    }
}

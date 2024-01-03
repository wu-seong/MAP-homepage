package map.homepage.auth.oauth2;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.user.User;
import map.homepage.domain.user.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.security.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    //1. 인증이 완료 됐으면 코드를 통해 토큰을 가져오고 토큰을 통해 유저 정보를 가져오기
    //2. 가져온 유저정보를 DB에 저장 혹은 갱신
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        //Provider와 사용자 이름을 가져옴
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String oauthId = oAuth2UserRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(provider, oauthId, oAuth2User.getAttributes());

        //유저정보와 provider를 넘겨 db에 저장시킨다
        User user = saveOrUpdate(attributes, oauthId);

        // Security Context에 저장되는 유저객체
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString())),
                attributes.getAttributes(),
                oauthId
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes, String oauthId) {
        User user = userRepository.findByOauthId(oauthId).orElseGet( () ->{
            User newUser = attributes.toEntity();
            newUser.setOauthId(oauthId);
            return userRepository.save(newUser);
        });
        user.update(attributes.getName(), attributes.getEmail(), attributes.getImageUri());
        return userRepository.save(user);
    }
}

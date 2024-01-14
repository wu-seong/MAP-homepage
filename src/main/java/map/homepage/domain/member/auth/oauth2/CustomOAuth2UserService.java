package map.homepage.domain.member.auth.oauth2;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.service.MemberService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberService memberService;
    //1. 인증이 완료 됐으면 코드를 통해 토큰을 가져오고 토큰을 통해 유저 정보를 가져오기
    //2. 가져온 유저정보를 DB에 저장 혹은 갱신
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        // Provider와 사용자 이름을 가져옴
        String provider = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        // oauth2 플랫폼 마다 응답 형식이 다르므로 구분해서 attribute를 구성
        OAuthAttributes attributes = OAuthAttributes.of(provider, oAuth2User.getAttributes());

        //유저정보와 provider를 넘겨 db에 저장시킨다
        Member member = memberService.saveOrUpdate(attributes);

        // Security Context에 저장되는 유저객체
        return new DefaultOAuth2User(
                //구글은 "sub" 라는 명확한 String value를, naver는 "response"내에 객체로 unique를 식별
                Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString())),
                oAuth2User.getAttributes(),
                userNameAttributeName
        );
    }
}

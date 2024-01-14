package map.homepage.domain.member.service;


import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.auth.oauth2.OAuthAttributes;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByOauthId(attributes.getOauthId()).orElseGet( () ->{
            Member newMember = attributes.toEntity();
            return memberRepository.save(newMember);
        });
        member.update(attributes.getName(), attributes.getEmail(), attributes.getImageUri());
        return memberRepository.save(member);
    }
}

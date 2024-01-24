package map.homepage.domain.member.service;


import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.exception.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(Member member){
        return memberRepository.save(member).getId();
    }

    public Member getMemberById(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return member;
    }

    public boolean isExistByOauthId(Long oauthId) {
        return memberRepository.findByOauthId(oauthId).isPresent();
    }

    public Member getMemberByOauthId(Long oauthId) {
        Member member = memberRepository.findByOauthId(oauthId).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return member;
    }
}

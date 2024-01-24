package map.homepage.domain.member.service;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.exception.GeneralException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{
    private final MemberRepository memberRepository;


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
    @Override
    public List<Member> getAllActive() {
        return memberRepository.findAll();
    }

    @Override
    public List<Member> getAll() {
        return null;
    }
}

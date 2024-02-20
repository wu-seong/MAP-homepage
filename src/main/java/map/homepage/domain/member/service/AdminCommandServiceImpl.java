package map.homepage.domain.member.service;


import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.exception.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class AdminCommandServiceImpl implements AdminCommandService{
    private final MemberQueryService memberQueryService;
    private final MemberRepository memberRepository;

    @Override
    public void withdrawMember(Long memberId) {
        Member member = memberQueryService.getMemberById(memberId);
        memberRepository.delete(member);
    }

    @Override
    public void grantAdmin(Long memberId) {
        Member member = memberQueryService.getMemberById(memberId);
        member.setRoleAdmin();
    }

    @Override
    public void depriveAdmin(Long memberId) {
        Member member = memberQueryService.getMemberById(memberId);
        member.setRoleUser();
    }
}

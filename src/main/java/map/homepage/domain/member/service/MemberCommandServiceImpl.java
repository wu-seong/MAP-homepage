package map.homepage.domain.member.service;


import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.dto.MemberRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService{
    private final MemberRepository memberRepository;


    @Override
    public Member create(Member member){
        return memberRepository.save(member);
    }

    @Override
    public Member update(MemberRequestDTO.MemberUpdateDTO updateDTO) {
        Member member = MemberContext.getMember();
        member.update(
                updateDTO.getStudentId(),
                updateDTO.getNickname(),
                updateDTO.getGrade()
        );
        if(!member.isInfoSet()) // 첫 정보기입(회원가입)이 완료 되면 Role 부여
            member.setMember();
        return member;
    }

    @Override
    public Member delete() {
        Member member = MemberContext.getMember();
        memberRepository.delete(member);
        return member;
    }
}

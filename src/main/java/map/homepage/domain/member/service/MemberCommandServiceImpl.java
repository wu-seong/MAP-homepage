package map.homepage.domain.member.service;


import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.dto.MemberRequestDTO;
import map.homepage.domain.member.enums.Status;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    public Member softDelete() {
        Member member = MemberContext.getMember();
        member.setInactive();
        return member;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 탈퇴 후 1달 지난 유저 디비 삭제
    public void hardDelete() {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Member> inactiveUsers = memberRepository.findByStatusAndInactiveDateBefore(Status.INACTIVE, oneMonthAgo);
        inactiveUsers.forEach(memberRepository::delete);
        // inactiveUsers.forEach(this::disconnectApp); 추후 구현..
    }
}

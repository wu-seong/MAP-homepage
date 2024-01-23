package map.homepage.domain.member.service;

import map.homepage.domain.member.Member;
import map.homepage.domain.member.dto.MemberRequestDTO;

public interface MemberCommandService {
    public Long create(Member member);

    public Member update(MemberRequestDTO.MemberUpdateDTO updateDTO);
}

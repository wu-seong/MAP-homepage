package map.homepage.domain.member.service;
import map.homepage.domain.member.Member;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MemberQueryService {
    public Member getMemberById(Long id);
    public Member getMemberByOauthId(String oauthId);
    public boolean isExistByOauthId(String oauthId);
    public Page<Member> getAllActive(Integer page);
    public Page<Member> getAll(Integer page);
}

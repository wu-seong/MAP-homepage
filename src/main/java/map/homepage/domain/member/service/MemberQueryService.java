package map.homepage.domain.member.service;

import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.exception.GeneralException;

public interface MemberQueryService {
    public Member getMemberById(Long id);
    public Member getMemberByOauthId(Long oauthId);
    public boolean isExistByOauthId(Long oauthId);
}

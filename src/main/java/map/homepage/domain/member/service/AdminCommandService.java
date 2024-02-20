package map.homepage.domain.member.service;

public interface AdminCommandService {
    public void withdrawMember(Long memberId);

    public void grantAdmin(Long memberId);

    public void depriveAdmin(Long memberId);
}

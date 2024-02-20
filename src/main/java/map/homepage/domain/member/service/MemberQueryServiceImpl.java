package map.homepage.domain.member.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.enums.Status;
import map.homepage.domain.member.profileImage.ProfileImage;
import map.homepage.domain.member.profileImage.repository.ProfileImageRepository;
import map.homepage.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class MemberQueryServiceImpl implements MemberQueryService{
    private static Integer countPerPage = 10;  // 페이지 당 나타낼 유저 수
    private final MemberRepository memberRepository;
    private final ProfileImageRepository profileImageRepository;

    public Member getMemberById(Long id){
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return member;
    }

    public boolean isExistByOauthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId).isPresent();
    }

    public Member getMemberByOauthId(String oauthId) {
        Member member = memberRepository.findByOauthId(oauthId).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_NOT_FOUND)
        );
        return member;
    }
    @Override
    public Page<Member> getAllActive(Integer page) {
        Page<Member> memberPage = memberRepository.findAllByStatus(Status.ACTIVE, PageRequest.of(page, countPerPage));
        memberPage.stream()
                .forEach(member -> {
                    ProfileImage profileImage = findProfileImageByMemberId(member.getId());
                    member.setProfileImage(profileImage);
                });
        return memberPage;
    }

    @Override
    public Page<Member> getAll(Integer page) {
        Page<Member> memberPage = memberRepository.findAll(PageRequest.of(page, countPerPage));
        memberPage.stream()
                .forEach(member -> {
                    ProfileImage profileImage = findProfileImageByMemberId(member.getId());
                    member.setProfileImage(profileImage);
                });
        return memberPage;
    }

    private ProfileImage findProfileImageByMemberId(Long memberId) {
        Member memberById = getMemberById(memberId);
        ProfileImage profileImage = profileImageRepository.findByMember(memberById).orElseThrow(() ->
                new GeneralException(ErrorStatus.USER_PROFILE_IMAGE_NOT_FOUND)
        );
        return profileImage;
    }
}

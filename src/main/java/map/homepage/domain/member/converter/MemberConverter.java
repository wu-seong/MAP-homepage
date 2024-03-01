package map.homepage.domain.member.converter;

import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.NaverOauth2DTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.enums.SocialType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


public class MemberConverter {
    public static Member toMember(KakaoOauth2DTO.UserInfoResponseDTO response){
        KakaoOauth2DTO.KakaoAccountDTO kakaoAccount = response.getKakaoAccount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dateString = kakaoAccount.getBirthyear() + kakaoAccount.getBirthday();
        LocalDate date = LocalDate.parse(dateString, formatter);
        return Member.builder()
                .oauthId(response.getId())
                .email(kakaoAccount.getEmail())
                .name(kakaoAccount.getName())
                .birth(date)
                .phoneNumber(kakaoAccount.getPhoneNumber())
                .socialType(SocialType.KAKAO)
                .role(Role.USER)
                .build();
    }
    public static Member toMember(NaverOauth2DTO.UserInfoResponseDTO response){
        NaverOauth2DTO.NaverAccountDTO naverAccount = response.getNaverAccount();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM-dd");
        String dateString = naverAccount.getBirthyear() + naverAccount.getBirthday();
        LocalDate date = LocalDate.parse(dateString, formatter);
        return Member.builder()
                .oauthId(naverAccount.getId())
                .email(naverAccount.getEmail())
                .name(naverAccount.getName())
                .socialType(SocialType.NAVER)
                .birth(date)
                .phoneNumber(naverAccount.getPhoneNumber())
                .role(Role.USER)
                .build();
    }

    public static MemberResponseDTO.LoginDTO toLoginDTO(Member member){
        return MemberResponseDTO.LoginDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .nickname(member.getNickname())
                .role(member.getRole().name())
                .infoSet(member.isInfoSet())
                .createdAt(member.getCreatedAt())
                .build();

    }
    public static MemberResponseDTO.MemberPreviewDTO toMemberPreviewDTO(Member member){
        return MemberResponseDTO.MemberPreviewDTO.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .grade(member.getGrade())
                .studentId(member.getStudentId())
                .profileImg(member.getProfileImage().getImageUrl())
                .build();
    }
    public static MemberResponseDTO.MemberPreviewListDTO toMemberPreviewListDTO(Page<Member> memberPage){
        List<MemberResponseDTO.MemberPreviewDTO> memberPreviewDTOList = memberPage.stream()
                .map(MemberConverter::toMemberPreviewDTO).collect(Collectors.toList());
        return MemberResponseDTO.MemberPreviewListDTO.builder()
                .isLast(memberPage.isLast())
                .isFirst(memberPage.isFirst())
                .totalPage(memberPage.getTotalPages())
                .totalElements(memberPage.getTotalElements())
                .listSize(memberPreviewDTOList.size())
                .memberPreviewDTOList(memberPreviewDTOList)
                .build();

    }

    public static MemberResponseDTO.MemberDetailDTO toMemberDetailDTO(Member member){
        return MemberResponseDTO.MemberDetailDTO.builder()
                .id(member.getId())
                .studentId(member.getStudentId())
                .oauthId(member.getOauthId())
                .email(member.getEmail())
                .name(member.getName())
                .socialType(member.getSocialType())
                .nickname(member.getNickname())
                .grade(member.getGrade())
                .birth(member.getBirth())
                .phoneNumber(member.getPhoneNumber())
                .imageUri(member.getProfileImage().getImageUrl())
                .infoSet(member.isInfoSet())
                .role(member.getRole())
                .status(member.getStatus())
                .inactiveDate(member.getInactiveDate())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }

    public static MemberResponseDTO.MemberDetailListDTO toMemberDetailListDTO(Page<Member> memberPage){
        List<MemberResponseDTO.MemberDetailDTO> memberDetailDTOList = memberPage.stream()
                .map(MemberConverter::toMemberDetailDTO).collect(Collectors.toList());
        return MemberResponseDTO.MemberDetailListDTO.builder()
                .isLast(memberPage.isLast())
                .isFirst(memberPage.isFirst())
                .totalPage(memberPage.getTotalPages())
                .totalElements(memberPage.getTotalElements())
                .listSize(memberDetailDTOList.size())
                .memberDetailDTOList(memberDetailDTOList)
                .build();

    }
}
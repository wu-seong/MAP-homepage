package map.homepage.domain.member.converter;

import map.homepage.domain.member.Member;
import map.homepage.domain.member.Role;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.enums.SocialType;
import map.homepage.domain.member.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberConverter {
    public static Member toMember(KakaoOauth2DTO.UserInfoResponseDTO response){
        KakaoOauth2DTO.KakaoAccountDTO kakaoAccount = response.getKakaoAccount();
        return Member.builder()
                .oauthId(response.getId())
                .email(kakaoAccount.getEmail())
                .name(kakaoAccount.getName())
                .build();
    }

    public static MemberResponseDTO.LoginDTO toLoginDTO(Member member){
        return MemberResponseDTO.LoginDTO.builder()
                .id(member.getId())
                .infoSet(member.isInfoSet())
                .createdAt(member.getCreatedAt())
                .build();

    }

    public static MemberResponseDTO.UserDetailDTO toMemberDetailDTO(Member member){
        return MemberResponseDTO.UserDetailDTO.builder()
                .id(member.getId())
                .oauthId(member.getOauthId())
                .email(member.getEmail())
                .name(member.getName())
                .socialType(member.getSocialType())
                .nickname(member.getNickname())
                .grade(member.getGrade())
                .birth(member.getBirth())
               // .imageUri(member.getImageUri())
                .infoSet(member.isInfoSet())
                .role(member.getRole())
                .status(member.getStatus())
                .inactiveDate(member.getInactiveDate())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
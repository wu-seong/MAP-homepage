package map.homepage.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.feedback.Feedback;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.service.AdminCommandService;
import map.homepage.domain.member.service.AdminQueryService;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberQueryService memberQueryService;
    private final AdminCommandService adminCommandService;
    private final AdminQueryService adminQueryService;
    @Operation(summary = "관리자 전용 유저 목록 조회 API",description = "유저의 세부 정보를 조회합니다.")
    @GetMapping("/members")
    public ApiResponse<MemberResponseDTO.MemberDetailListDTO> getMemberInfos(@RequestParam Integer page){
            Page<Member> MemberPage = memberQueryService.getAll(page-1);
            return ApiResponse.onSuccess(MemberConverter.toMemberDetailListDTO(MemberPage)); //관리자는 세부 정보를 획득
    }
    @Operation(summary = "관리자 전용 유저 추방 API",description = "지정된 회원을 회원 탈퇴 시킵니다.")
    @DeleteMapping("/members/{member-id}")
    public ApiResponse<String> deleteMember(@PathVariable(value = "member-id") Long memberId){
        adminCommandService.withdrawMember(memberId);
        return ApiResponse.onSuccess(String.valueOf(memberId) + "번 회원 삭제 성공"); //관리자는 세부 정보를 획득
    }

    @Operation(summary = "관리자 권한 부여 API",description = "일반 회원에게 관리자 권한을 부여합니다.")
    @PatchMapping("/members/{member-id}/role/admin")
    public ApiResponse<String> grantAdminRole(@PathVariable(value = "member-id") Long memberId){
        adminCommandService.grantAdmin(memberId);
        return ApiResponse.onSuccess(String.valueOf(memberId) + "번 회원 관리자 권한 부여");
    }
    @Operation(summary = "관리자 권한 회수 API",description = "지정된 회원의 관리자 권한을 회수합니다.")
    @PatchMapping("/members/{member-id}/role/user")
    public ApiResponse<String> depriveAdminRole(@PathVariable(value = "member-id") Long memberId){
        adminCommandService.depriveAdmin(memberId);
        return ApiResponse.onSuccess(String.valueOf(memberId) + "번 회원 관리자 권한 회수");
    }

    @Operation(summary = "피드백 조회 API")
    @GetMapping("/feedbacks")
    public ApiResponse<List<String>> getFeedbacks(){
        List<Feedback> feedbackList = adminQueryService.getFeedbackList();
        List<String> feedbackContentList = feedbackList.stream()
                .map(feedback -> feedback.getContent())
                .collect(Collectors.toList());
        return ApiResponse.onSuccess(feedbackContentList);
    }
}

package map.homepage.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.dto.MemberRequestDTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.service.MemberCommandService;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @PatchMapping("/me")
    @Operation(summary = "추가 정보 입력/수정 API",description = "소셜 로그인 인증 성공 이후에 호출할 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =" 추가 정보 입력 성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.MemberDetailDTO.class))),
        //@ApiResponse(responseCode = "404", description = "인증 토큰이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ApiResponse<MemberResponseDTO.MemberDetailDTO> addProfile(@RequestBody MemberRequestDTO.MemberUpdateDTO memberUpdateDTO){
        // 추가 정보 받아와서 유저에 넣어준뒤에 DB에 저장
        Member updatedMember = memberCommandService.update(memberUpdateDTO);
        return ApiResponse.onSuccess(MemberConverter.toMemberDetailDTO(updatedMember));
    }

    @DeleteMapping("/me")
    @Operation(summary = "멤버 삭제 API",description = "softDelete로 삭제 했지만 ?일 뒤에 완전 삭제")
    public ApiResponse<MemberResponseDTO.MemberPreviewDTO> deleteMember(){
        Member deletedMember = memberCommandService.softDelete();
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(deletedMember));
    }
    @Operation(summary = "본인 정보 조회 API",description = "기본 정보만 조회")
    @GetMapping("/me/preview")
    public ApiResponse<MemberResponseDTO.MemberPreviewDTO> getMemberPreview(){
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }
    @Operation(summary = "본인 세부 정보 조회 API",description = "세부 정보 조회")
    @GetMapping("/me/detail")
    public ApiResponse<MemberResponseDTO.MemberDetailDTO> getMemberDetail(){
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(MemberConverter.toMemberDetailDTO(member));
    }
    @Operation(summary = "다른 사용자 정보 조회 API",description = "다른 사용자 프로필 조회 시 사용, 민감하지 않은 정보만 조회 가능 하도록 학번 넣을지 뺄지 고민 중")
    @GetMapping("/{member-id}/preview") // 다른 사용자 프로필 조회
    public ApiResponse<MemberResponseDTO.MemberPreviewDTO> getOtherMemberPreview(@PathVariable(name = "member-id") Long otherId){
        Member other = memberQueryService.getMemberById(otherId);
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(other));
    }


    @Operation(summary = "유저 목록 조회 API",description = "유저 기본 정보 목록을 조회합니다.")
    @GetMapping("")
    public ApiResponse<MemberResponseDTO.MemberPreviewListDTO> getMemberInfos(@RequestParam Integer page){
        Page<Member> activeMemberPage = memberQueryService.getAllActive(page);
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewListDTO(activeMemberPage));
    }
}

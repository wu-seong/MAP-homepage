package map.homepage.domain.member;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.dto.MemberRequestDTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.service.MemberCommandService;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Member deletedMember = memberCommandService.delete();
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(deletedMember));
    }
    @GetMapping("/me/preview")
    public ApiResponse<MemberResponseDTO.MemberPreviewDTO> getMemberPreview(){
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(member));
    }
    @GetMapping("/me/detail")
    public ApiResponse<MemberResponseDTO.MemberDetailDTO> getMemberDetail(){
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(MemberConverter.toMemberDetailDTO(member));
    }
    @GetMapping("/{member-id}/preview") // 다른 사용자 프로필 조회
    public ApiResponse<MemberResponseDTO.MemberPreviewDTO> getOtherMemberPreview(@PathVariable(name = "member-id") Long otherId){
        Member other = memberQueryService.getMemberById(otherId);
        return ApiResponse.onSuccess(MemberConverter.toMemberPreviewDTO(other));
    }

    @GetMapping("")
    public ApiResponse<?> getMemberInfos(@RequestParam Integer page){
        Member member = MemberContext.getMember();
        if(!member.isAdmin()){
            Page<Member> activeMemberPage = memberQueryService.getAllActive(page);
            return ApiResponse.onSuccess(MemberConverter.toMemberPreviewListDTO(activeMemberPage));
        }
        else{
            Page<Member> MemberPage = memberQueryService.getAll(page);
            return ApiResponse.onSuccess(MemberConverter.toMemberDetailListDTO(MemberPage)); //관리자는 세부 정보를 획득
        }
    }
}

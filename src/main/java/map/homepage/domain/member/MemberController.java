package map.homepage.domain.member;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.dto.MemberRequestDTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.service.MemberCommandService;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;

    @PatchMapping("/")
    @Operation(summary = "추가 정보 입력 API",description = "소셜 로그인 인증 성공 이후에 호출할 API")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description =" 추가 정보 입력 성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.MemberDetailDTO.class))),
        //@ApiResponse(responseCode = "404", description = "인증 토큰이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ApiResponse<MemberResponseDTO.MemberDetailDTO> addProfile(@RequestBody MemberRequestDTO.MemberUpdateDTO memberUpdateDTO){
        // 추가 정보 받아와서 유저에 넣어준뒤에 DB에 저장
        Member updatedMember = memberCommandService.update(memberUpdateDTO);
        return ApiResponse.onSuccess(MemberConverter.toMemberDetailDTO(updatedMember));
    }
}

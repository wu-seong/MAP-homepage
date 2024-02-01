package map.homepage.domain.member.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.dto.MemberResponseDTO;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final MemberQueryService memberQueryService;
    @Operation(summary = "유저 목록 조회 API",description = "일반 사용자는 민감하지 않은 기본 정보만, 관리자는 세부정보까지 얻음." +
            " 근데 이거 나중에 관리자 기능만 따로 빼서 API path 따로 만들듯 ")
    @GetMapping("")
    public ApiResponse<MemberResponseDTO.MemberDetailListDTO> getMemberInfos(@RequestParam Integer page){
            Page<Member> MemberPage = memberQueryService.getAll(page);
            return ApiResponse.onSuccess(MemberConverter.toMemberDetailListDTO(MemberPage)); //관리자는 세부 정보를 획득
    }
}

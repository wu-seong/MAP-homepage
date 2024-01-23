package map.homepage.domain.member;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import map.homepage.domain.member.dto.MemberRequestDTO;
import map.homepage.domain.member.dto.MemberResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    @PostMapping("/user")
    @Operation(summary = "추가 정보 입력 API",description = "소셜 로그인 인증 성공 이후에 호출할 API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description =" 추가 정보 입력 성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.MemberDetailDTO.class))),
        //@ApiResponse(responseCode = "404", description = "인증 토큰이 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MemberResponseDTO.MemberDetailDTO addProfile(@RequestBody MemberRequestDTO.MemberSignUpDTO memberSignUpDTO){

        return null;
    }
}

package map.homepage.domain.member.auth.oauth2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.apiPayload.code.status.SuccessStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.auth.jwt.service.JwtUtil;
import map.homepage.domain.member.auth.jwt.token.JwtToken;
import map.homepage.domain.member.auth.oauth2.feignClient.dto.KakaoOauth2DTO;
import map.homepage.domain.member.auth.oauth2.service.LoginService;
import map.homepage.domain.member.converter.MemberConverter;
import map.homepage.domain.member.service.MemberCommandService;
import map.homepage.domain.member.service.MemberQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final JwtUtil jwtUtil;


    /*
    테스트용 API, CORS 때문에 직접 호출하지 않고 redirect
     */

    @Operation(summary = "카카오 인증 페이지 이동 API", description = "redirect를 통해 카카오 인증 페이지로 이동")
    @GetMapping("/oauth2/authorize/kakao")
    public String login(){
        String redirectUri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", "32c0787d1b1e9fcabcc24af247903ba8")
                .queryParam("redirect_uri", "http://localhost:3000/oauth2/login/kakao")
                .toUriString();
        return "redirect:" + redirectUri;
    }
    /**
         1. 토큰 얻기
         2. 사용자 정보 얻어오기
         3. DB 조회 및 추가
         4. 응답 헤더에 Jwt 토큰 추가
      */
    @Operation(summary = "소셜 로그인 인증 API", description = "토큰으로 정보 조회 및 저장, 성공시 응답 헤더에 jwt토큰 추가")
    @ResponseBody
    @GetMapping("/oauth2/login/kakao")
    public ApiResponse<?> getAccessToken(HttpServletResponse response, @RequestParam(name = "code") String code){
        String accessToken = loginService.getAccessToken(code);
        accessToken = "Bearer " + accessToken;
        // ok -> 유저 정보 가져오기
        KakaoOauth2DTO.UserInfoResponseDTO userInfoResponseDTO = null;
        try {
            userInfoResponseDTO = loginService.getUserInfo(accessToken);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        // 유저 정보에 DB 조회하고 정보 있으면 응답만, 없으면 저장까지, 추가정보 입력 여부에 따라서 응답 다르게
        Long oauthId = userInfoResponseDTO.getId();
        if( memberQueryService.isExistByOauthId(oauthId)){
            Member member = memberQueryService.getMemberByOauthId(oauthId);
            // 응답헤더에 토큰 추가
            JwtToken token = jwtUtil.generateToken(String.valueOf(member.getId()), Role.USER);
            response.addHeader("Access-Token", token.getAccessToken());
            response.addHeader("Refresh-Token", token.getRefreshToken());
            if(member.isInfoSet()){
                return ApiResponse.onSuccess(MemberConverter.toLoginDTO(member));
            }
            return ApiResponse.of(SuccessStatus.NEED_USER_DETAIL, MemberConverter.toLoginDTO(member));
        }
        else{
            Member user = MemberConverter.toMember(userInfoResponseDTO);
            Long id = memberCommandService.create(user);
            // 응답헤더에 토큰 추가
            JwtToken token = jwtUtil.generateToken(String.valueOf(id), Role.USER);
            response.addHeader("Access-Token", token.getAccessToken());
            response.addHeader("Refresh-Token", token.getRefreshToken());
            return ApiResponse.of(SuccessStatus.NEED_USER_DETAIL, MemberConverter.toLoginDTO(user));
        }
    }


    /*
   테스트용 API
    */
//    @Operation(summary = "UserContext Test  API",description = "jwt 토큰 검증 성공 시 유저 객체 저장한 거 조회 가능한지 테스트")
//    @GetMapping("/test")
//    public ResponseEntity<String> testAfterGetToken(){
//        log.info("authenticatedUser = {}", UserContext.getUser()); // 테스트 성공
//        return ResponseEntity.ok("");
//    }
}
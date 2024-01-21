package map.homepage.domain.member.auth.jwt.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {

    @Operation(summary = "테스트 API",description = "토큰검증 후 파싱하여 유저 객체 전역적으로 생성하는지 테스트")
    @GetMapping("/test")
    public Member test(){
        Member member = MemberContext.getMember();
        return member;
    }
}

package map.homepage.domain.member.auth.jwt.controller;


import lombok.extern.slf4j.Slf4j;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/test")
    public Member test(){
        Member member = MemberContext.getMember();
        return member;
    }
}

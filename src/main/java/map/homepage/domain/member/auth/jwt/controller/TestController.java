package map.homepage.domain.member.auth.jwt.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        System.out.println("요청 무사 전달");
        return "ok!";
    }
}

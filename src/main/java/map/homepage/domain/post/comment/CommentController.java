package map.homepage.domain.post.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    @GetMapping("/hello")
    public String hello(){
        return "hello swagger";
    }
}

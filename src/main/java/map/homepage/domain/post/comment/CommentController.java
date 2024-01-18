package map.homepage.domain.post.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController // RestApi 사용 시
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
@RequestMapping("/api") // 들어온 요청을 특정 method와 매핑하기 위해 사용
public class CommentController {
    @GetMapping("/hello")
    public String hello(){
        return "hello swagger";
    }
}

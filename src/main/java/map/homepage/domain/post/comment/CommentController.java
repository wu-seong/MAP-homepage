package map.homepage.domain.post.comment;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.post.comment.Dto.CommentCreateRequest;
import map.homepage.domain.post.comment.Dto.CommentReadCondition;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController // RestApi 사용 시
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
@RequestMapping("/api") // 들어온 요청을 특정 method와 매핑하기 위해 사용
public class CommentController {
    private  CommentService commentService;
    private  MemberRepository memberRepository;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회 (불러오기)
    @ApiResponses
    @GetMapping("/comment/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getComment(@Valid CommentReadCondition commentReadCondition) {
        return new Response();
    }

    // 댓글 생성
    @PostMapping("/comment/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response writeComment(@Valid @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication) {
        return new Response();
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{post_id}/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, Authentication authentication){
        return new Response();
    }
}

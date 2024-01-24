package map.homepage.domain.post.comment;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.post.comment.Dto.CommentCreateRequest;
import map.homepage.domain.post.comment.Dto.CommentDto;
import map.homepage.domain.post.comment.Dto.CommentReadCondition;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getComment(@RequestParam @Valid CommentReadCondition commentReadCondition) {
        return commentService.getComment(commentReadCondition);
    }

    // 댓글 생성
    @PostMapping("/comment/{post_id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto writeComment(@Valid @RequestBody @PathVariable("post_id") CommentCreateRequest commentCreateRequest, Member memberId) {
        return commentService.writeComment(commentCreateRequest, memberId);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{comment_id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteComment(@PathVariable("comment_id") Long commentId, Member memberId){
        return commentService.deleteComment(commentId, memberId);
    }
}

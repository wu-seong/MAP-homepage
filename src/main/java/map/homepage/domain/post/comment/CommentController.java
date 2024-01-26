package map.homepage.domain.post.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.post.comment.dto.CommentCreateRequest;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.domain.post.comment.dto.CommentReadCondition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController // RestApi 사용 시
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
@RequestMapping("/comments") // 들어온 요청을 특정 method와 매핑하기 위해 사용
public class CommentController {
    private  CommentService commentService;
    private  MemberRepository memberRepository;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 조회 (불러오기)
    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CommentDto>> getComment(@Valid CommentReadCondition commentReadCondition) {
        List<CommentDto> commentDtoList = commentService.getComment(commentReadCondition);
        return ResponseEntity.ok(commentDtoList);
    }

    // 댓글 생성
    @PostMapping("/{post_id}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto writeComment(@RequestBody CommentCreateRequest commentCreateRequest, @PathVariable("post_id") Long postId) {
        return commentService.writeComment(commentCreateRequest, postId);
    }

    // 댓글 삭제
    @DeleteMapping("/{comment_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("comment_id") Long commentId){
        commentService.deleteComment(commentId);
    }
}

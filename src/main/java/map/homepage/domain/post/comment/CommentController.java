package map.homepage.domain.post.comment;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.comment.dto.CommentDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController // RestApi 사용 시
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
@RequestMapping("/comments") // 들어온 요청을 특정 method와 매핑하기 위해 사용
@Validated
public class CommentController {
    private  final CommentServiceImpl commentService;

    // 댓글 조회 (불러오기)
    @GetMapping("/{post-id}")
    @Operation(summary = "댓글 조회 API",description = "게시물에 대한 전체 댓글 조회")
    public ApiResponse<List<CommentDto>> getComment(@Valid @PathVariable("post-id") Long postId) {
        return ApiResponse.onSuccess(commentService.getComment(postId));
    }

    // 댓글 생성
    @PostMapping("/{post-id}")
    @Operation(summary = "댓글 작성 API",description = "게시물에 대한 새로운 댓글 작성")
    public ApiResponse<CommentDto> writeComment(@RequestBody String content, @Valid @PathVariable("post-id") Long postId) {
        Comment createComment = commentService.writeComment(content, postId);
        return ApiResponse.onSuccess(CommentDto.toDto(createComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{comment-id}")
    @Operation(summary = "댓글 삭제 API",description = "softDelete")
    public ApiResponse<CommentDto> deleteComment(@Valid @PathVariable("comment-id") Long commentId){
        Member member = MemberContext.getMember();
        Comment deleteComment = commentService.deleteComment(commentId, member);
        return ApiResponse.onSuccess(CommentDto.toDto(deleteComment));
    }
}

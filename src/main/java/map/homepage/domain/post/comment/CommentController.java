package map.homepage.domain.post.comment;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.post.comment.dto.CommentCreateRequest;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.domain.post.comment.dto.CommentReadCondition;
import org.springframework.web.bind.annotation.*;

// 사용자(Front-End) 와 백엔드를 연결해주는 역할
@RestController // RestApi 사용 시
@RequiredArgsConstructor // final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 만듦
@RequestMapping("/comments") // 들어온 요청을 특정 method와 매핑하기 위해 사용
public class CommentController {
    private  final CommentServiceImpl commentService;

    // 댓글 조회 (불러오기)
    @GetMapping("/")
    @Operation(summary = "댓글 조회 API",description = "게시물에 대한 전체 댓글 조회")
    public ApiResponse<?> getComment(@Valid CommentReadCondition commentReadCondition) {
        // List<CommentDto> commentDtoList = commentService.getComment(commentReadCondition);
        return ApiResponse.onSuccess(commentService.getComment(commentReadCondition));
    }

    // 댓글 생성
    @PostMapping("/{post_id}")
    @Operation(summary = "댓글 작성 API",description = "게시물에 대한 새로운 댓글 작성")
    public ApiResponse<CommentDto> writeComment(@RequestBody CommentCreateRequest commentCreateRequest, @PathVariable("post_id") Long postId) {
        Comment createComment = commentService.writeComment(commentCreateRequest, postId);
        return ApiResponse.onSuccess(CommentDto.toDto(createComment));
    }

    // 댓글 삭제
    @DeleteMapping("/{comment_id}")
    @Operation(summary = "댓글 삭제 API",description = "softDelete")
    public ApiResponse<CommentDto> deleteComment(@PathVariable("comment_id") Long commentId){
        Comment deleteComment = commentService.deleteComment(commentId);
        return ApiResponse.onSuccess(CommentDto.toDto(deleteComment));
        // MemberContext.getMember() -> 이후에
    }
}

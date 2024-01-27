package map.homepage.domain.post.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {
    // @NotNull(message = "게시글 번호를 입력해주세요.") // null만 허용하지 않음
    // @Positive(message = "게시글 번호를 입력해주세요.") // 양수만 가능
    // private Long postId;

    @NotBlank(message = "댓글을 입력해주세요.") // null과 "", " "  모두 허용하지 않음
    private String content;
}

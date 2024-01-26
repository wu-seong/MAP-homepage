package map.homepage.domain.post.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReadCondition {
    @NotNull(message = "게시글 번호를 입력해주세요.")
    @Positive(message = "올바른 게시글 번호를 입력해주세요.")
    private Long postId;
}

package map.homepage.domain.post.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import map.homepage.domain.post.comment.Comment;

@Data // getter, setter 만들어줌
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬
public class CommentDto {
    private Long id;
    private String content;
    private String writer;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getName()
        );
    }
}

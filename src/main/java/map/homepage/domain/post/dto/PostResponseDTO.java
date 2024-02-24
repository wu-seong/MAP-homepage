package map.homepage.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.dto.general.GeneralPostResponseDTO;

import java.time.LocalDateTime;


@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Long postId;
    private int views;
    private String content;
    private String dtype;
    private Role role;
    private String title;
    private LocalDateTime createdAt;
    private Integer totalComment;
    private Long writerId;
    private String writerName;
}

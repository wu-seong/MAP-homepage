package map.homepage.domain.post.dto;

import lombok.*;
import map.homepage.domain.member.Role;
import map.homepage.domain.post.Post;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {

    private Long postId;
    private int views;
    private String content;
    private String dtype;
    private Role role;
    private String title;
    private LocalDateTime createdAt;

    public static PostResponseDTO fromEntity(Post post) {
        PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(post.getId());
        dto.setViews(post.getViews());
        dto.setContent(post.getContent());
        dto.setDtype(post.getDtype());
        dto.setRole(post.getRole());
        dto.setTitle(post.getTitle());
        dto.setCreatedAt(post.getCreatedAt());
        return dto;
    }

}

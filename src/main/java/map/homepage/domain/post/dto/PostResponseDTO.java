// PostResponseDTO.java
package map.homepage.domain.post.dto;

import lombok.*;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.post.Post;

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
    private Long memberId;

    private String nickname;

    // 게시글 목록 조회
    public static PostResponseDTO fromEntity(Post post) { 
        PostResponseDTO dto = new PostResponseDTO();
        dto.setPostId(post.getId());
        dto.setViews(post.getViews());
        dto.setContent(post.getContent());
        dto.setDtype(post.getDtype());
        dto.setRole(post.getRole());
        dto.setTitle(post.getTitle());
        dto.setCreatedAt(post.getCreatedAt());

        dto.setMemberId(post.getMember().getId());
        dto.setNickname(post.getMember().getNickname());
        return dto;
    }
}
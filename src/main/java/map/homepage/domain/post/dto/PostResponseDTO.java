package map.homepage.domain.post.dto;

import lombok.*;
import map.homepage.domain.post.Post;

@Setter
public class PostResponseDTO {

    private Long id;
    private String title;
    private String content;

    // 생성자
    public PostResponseDTO(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // 엔티티로부터 DTO 생성
    public static PostResponseDTO fromEntity(Post post) {
        return new PostResponseDTO(post.getId(), post.getTitle(), post.getContent());
    }

    // Getter 메서드들은 필요에 따라 추가
    public Long getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
}

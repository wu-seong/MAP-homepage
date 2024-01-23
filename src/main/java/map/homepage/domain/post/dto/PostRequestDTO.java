package map.homepage.domain.post.dto;

import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import map.homepage.domain.member.Member;

@Data
public class PostRequestDTO {

    private String title;
    private String content;

    // 생성자
    public PostRequestDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getter와 Setter 메서드는 필요에 따라 추가

}

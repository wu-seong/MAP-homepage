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
    private String dtype;
    private String content;

    // 생성자
    public PostRequestDTO(String title, String dtype, String content) {
        this.title = title;
        this.dtype = dtype;
        this.content = content;
    }

}


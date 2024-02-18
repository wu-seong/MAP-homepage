// PostRequestDTO.java
package map.homepage.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostRequestDTO {
    @Schema(example = "제목")
    private String title;

    @Schema(example = "타입")
    private String dtype;

    @Schema(example = "내용")
    private String content;
}
package map.homepage.domain.post.dto.general;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.post.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneralPostPreviewResponseDTO {
    private boolean notice;
    private Long postId;
    private String title;
    private Integer view;
    private LocalDateTime uploadedTime;
    private Long writerId;
    private String writerName;

}

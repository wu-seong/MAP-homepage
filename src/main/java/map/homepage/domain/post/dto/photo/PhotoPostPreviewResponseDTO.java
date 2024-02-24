package map.homepage.domain.post.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.dto.general.GeneralPostPreviewResponseDTO;
import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.converter.ImageConverter;
import map.homepage.domain.post.image.dto.ImageResponseDTO;
import map.homepage.domain.post.image.dto.ImageResponseListDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoPostPreviewResponseDTO {
    private Long postId;
    private boolean notice;
    private String title;
    private Integer view;
    private LocalDate uploadedTime;
    private Long writerId;
    private String writerName;
    private String thumbnail;

}

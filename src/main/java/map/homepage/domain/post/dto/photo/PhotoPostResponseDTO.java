package map.homepage.domain.post.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.image.dto.ImageResponseListDTO;

import java.time.LocalDateTime;


@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PhotoPostResponseDTO extends PostResponseDTO {
    private boolean notice;
    private ImageResponseListDTO imageResponseListDTO;
}

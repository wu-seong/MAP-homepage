package map.homepage.domain.post.image.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseDTO;

import java.util.List;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseListDTO {
    private List<ImageResponseDTO> imageResponseDTOList;
    private Integer listSize;

}

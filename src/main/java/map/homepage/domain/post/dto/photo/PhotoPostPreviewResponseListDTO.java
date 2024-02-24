package map.homepage.domain.post.dto.photo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotoPostPreviewResponseListDTO {
    private List<PhotoPostPreviewResponseDTO> photoPostResponseDTOList;
    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private Boolean isFirst;
    private Boolean isLast;
    private Integer nowPage;
}

package map.homepage.domain.post.dto.general;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class GeneralPostPreviewResponseListDTO {
    private List<GeneralPostPreviewResponseDTO> postResponseDTOList;
    private Integer listSize;
    private Integer totalPage;
    private Long totalElements;
    private Boolean isFirst;
    private Boolean isLast;
    private Integer nowPage;
}

package map.homepage.domain.post.converter;

import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.dto.PostResponseListDTO;
import org.springframework.data.domain.Page;

public class PostConverter {
    public static PostResponseListDTO toPostResponseListDTO(Page<PostResponseDTO> photoPostPage, int page) {
        return PostResponseListDTO.builder()
                .postResponseDTOList(photoPostPage.getContent())
                .listSize(photoPostPage.getNumberOfElements())
                .totalPage(photoPostPage.getTotalPages())
                .totalElements(photoPostPage.getTotalElements())
                .isFirst(photoPostPage.isFirst())
                .isLast(photoPostPage.isLast())
                .nowPage(page)
                .build();
    }
}

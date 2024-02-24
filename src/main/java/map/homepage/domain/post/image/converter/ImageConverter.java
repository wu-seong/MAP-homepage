package map.homepage.domain.post.image.converter;

import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.dto.ImageResponseDTO;
import map.homepage.domain.post.image.dto.ImageResponseListDTO;

import java.util.List;

public class ImageConverter {

    public static ImageResponseDTO fromEntity(Image image){
        return ImageResponseDTO.builder()
                .id(image.getId())
                .originalName(image.getOriginalName())
                .storageName(image.getStorageName())
                .imageUrl(image.getImageUrl())
                .build();
    }
    public static ImageResponseListDTO toImageResponseListDTO(List<ImageResponseDTO> imageResponseDTOList) {
        return ImageResponseListDTO.builder()
                .imageResponseDTOList(imageResponseDTOList)
                .listSize(imageResponseDTOList.size())
                .build();
    }
}

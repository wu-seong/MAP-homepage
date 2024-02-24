package map.homepage.domain.post.image.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponseDTO {
    private Long id;
    private String originalName;
    private String storageName;
    private String imageUrl;
}

package map.homepage.domain.post.attachedFile.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttachedFileResponseDTO {
    private Long id;
    private String originalName;
    private String storageName;
    private String fileUrl;
}

// PostResponseDTO.java
package map.homepage.domain.post.dto.general;

import lombok.*;
import lombok.experimental.SuperBuilder;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.attachedFile.dto.AttachedFileResponseDTO;
import map.homepage.domain.post.dto.PostResponseDTO;

import java.time.LocalDateTime;

@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralPostResponseDTO extends PostResponseDTO {
    private boolean isNotice;
    private AttachedFileResponseDTO attachedFileResponseDTO;
}
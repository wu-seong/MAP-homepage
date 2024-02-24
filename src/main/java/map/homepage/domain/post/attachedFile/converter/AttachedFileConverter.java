package map.homepage.domain.post.attachedFile.converter;

import map.homepage.domain.post.attachedFile.AttachedFile;
import map.homepage.domain.post.attachedFile.dto.AttachedFileResponseDTO;

public class AttachedFileConverter {
    public static AttachedFileResponseDTO fromEntity(AttachedFile attachedFile){
        return AttachedFileResponseDTO.builder()
                .id(attachedFile.getId())
                .originalName(attachedFile.getOriginalName())
                .storageName(attachedFile.getStorageName())
                .fileUrl(attachedFile.getFileUrl())
                .build();
    }
}

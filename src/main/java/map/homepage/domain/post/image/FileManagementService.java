//ImageService.java
package map.homepage.domain.post.image;

import map.homepage.domain.post.Post;
import map.homepage.domain.post.attachedFile.AttachedFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public interface FileManagementService {
    Image uploadImage(Post post, MultipartFile file);
    AttachedFile uploadFile(Post post, MultipartFile file) throws IOException;
    void deleteImage(Long imageId);
    void deleteFile(AttachedFile file);
}

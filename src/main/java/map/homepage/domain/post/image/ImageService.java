//ImageService.java
package map.homepage.domain.post.image;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageService {
    String uploadImage(Long postId, MultipartFile file) throws IOException;
    void deleteImage(Long imageId);
}

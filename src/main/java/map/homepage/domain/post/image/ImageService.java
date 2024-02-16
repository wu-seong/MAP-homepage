//ImageService.java
package map.homepage.domain.post.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public interface ImageService {
    String uploadImage(Long postId, MultipartFile file) throws IOException;
    String uploadFile(MultipartFile file) throws IOException;
    void deleteImage(Long imageId);
    void deleteFile(String url);
}

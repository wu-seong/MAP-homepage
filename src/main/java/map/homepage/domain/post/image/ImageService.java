//ImageService.java
package map.homepage.domain.post.image;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface ImageService {
    String uploadImage(MultipartFile file) throws IOException;
}

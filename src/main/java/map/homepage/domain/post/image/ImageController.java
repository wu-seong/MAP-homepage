//Imagecontroller.java
package map.homepage.domain.post.image;

import lombok.RequiredArgsConstructor;
import map.homepage.exception.ImageUploadException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{post_id}")
    public ResponseEntity<String> uploadImage(
            @PathVariable("post_id") Long postId,
            @RequestParam("file") MultipartFile file) {
        try {
            // 이미지를 업로드하고 URL을 반환하는 서비스 메소드 호출
            String imageUrl = imageService.uploadImage(postId, file);
            return ResponseEntity.ok(imageUrl);
        } catch (IOException e) {
            throw new ImageUploadException("이미지 업로드 중 오류가 발생했습니다.");
        }
    }
}

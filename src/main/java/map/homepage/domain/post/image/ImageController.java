//ImageController.java
package map.homepage.domain.post.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    // 사진 업로드
//    @PostMapping("/{post_id}")
//    public ResponseEntity<String> uploadImage(Long postId,
//            @RequestParam("file") MultipartFile file) {
//        try {
//            // 이미지를 업로드하고 URL을 반환하는 서비스 메소드 호출
//            String imageUrl = imageService.uploadImage(postId,file);
//            return ResponseEntity.ok(imageUrl);
//        } catch (IOException e) {
//            throw new ImageUploadException("사진 업로드 중 오류가 발생했습니다.");
//        }
//    }

    // 사진 삭제
    @DeleteMapping("/{image_id}")
    public ResponseEntity<String> deleteImage(@PathVariable("image_id") Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("사진이 성공적으로 삭제되었습니다.");
    }
}

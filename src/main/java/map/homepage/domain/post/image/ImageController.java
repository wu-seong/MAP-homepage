//ImageController.java
package map.homepage.domain.post.image;

import io.swagger.v3.oas.annotations.Operation;
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

    // 사진 삭제
    @DeleteMapping("/{image-id}")
    @Operation(summary = "사진 삭제 API")
    public ResponseEntity<String> deleteImage(
            @PathVariable("image-id") Long imageId
    ) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("사진이 성공적으로 삭제되었습니다.");
    }
}

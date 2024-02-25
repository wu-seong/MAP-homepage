//ImageServiceImpl.java
package map.homepage.domain.post.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.attachedFile.AttachedFile;
import map.homepage.exception.GeneralException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileManagementServiceImpl implements FileManagementService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public Image uploadImage(Post post, MultipartFile file) {
        String originalName = file.getOriginalFilename();
        String storageName = "images/" + UUID.randomUUID() + "_" + originalName;
        String imageUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + storageName;

        // AWS S3에 이미지 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        try {
            amazonS3Client.putObject(bucket, storageName, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 이미지 정보 저장
        Image image = new Image();
        image.setOriginalName(originalName);
        image.setStorageName(storageName);
        image.setImageUrl(imageUrl);
        image.setPost(post);
        // 업로드된 이미지의 썸네일 URL 반환
        return image; // 나중에 수정
    }

    @Override
    public AttachedFile uploadFile(Post post, MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String storageName = "files/" + UUID.randomUUID() + "_" + originalName; // 나중에 수정
        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + storageName;

        AttachedFile attachedFile = AttachedFile.builder()
                .originalName(originalName)
                .storageName(storageName)
                .fileUrl(fileUrl)
                .post(post)
                .build();

        // AWS S3에 파일 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, storageName, file.getInputStream(), metadata);

        return attachedFile;
    }

    @Override
    public void deleteImage(Long imageId) {
        // 이미지 정보를 데이터베이스에서 삭제
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.IS_NOT_IMAGE));
        imageRepository.delete(image);

        // AWS S3에서 이미지 삭제
        String storagePath = image.getStorageName();
        amazonS3Client.deleteObject(bucket, storagePath);
    }

    @Override
    public void deleteFile(AttachedFile attachedFile){
        String url = attachedFile.getFileUrl();
        String storagePath = url.substring(("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/").length());
        amazonS3Client.deleteObject(bucket, storagePath);
    }
}
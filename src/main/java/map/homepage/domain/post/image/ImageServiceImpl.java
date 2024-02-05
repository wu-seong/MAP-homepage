//ImageServiceImpl.java
package map.homepage.domain.post.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String storagePath = "images/" + UUID.randomUUID() + "_" + originalName;

        // AWS S3에 이미지 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, storagePath, file.getInputStream(), metadata);

        // 이미지 정보 저장
        Image image = new Image();
        image.setOriginalName(originalName);
        image.setStoragePath(storagePath);
        imageRepository.save(image); // 이미지 정보를 데이터베이스에 저장

        // 업로드된 이미지의 URL 반환
        return "https://" + bucket + "/" + storagePath;
    }
}

//ImageServiceImpl.java
package map.homepage.domain.post.image;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.exception.GeneralException;
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
    private final PostRepository postRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImage(Long postId, MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        String storageName = "images/" + UUID.randomUUID() + "_" + originalName;
        String imageUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + storageName;

        // AWS S3에 이미지 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, storageName, file.getInputStream(), metadata);

        // postId를 사용하여 Post 엔티티를 가져옴
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        // 이미지 정보 저장
        Image image = new Image();
        image.setOriginalName(originalName);
        image.setStorageName(storageName);
        image.setImageUrl(imageUrl);
        image.setPost(post);
        imageRepository.save(image); // 이미지 정보를 데이터베이스에 저장

        // 업로드된 이미지의 썸네일 URL 반환
        return "https://d3djt9dt9ouox.cloudfront.net/"+storageName; // 나중에 수정
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();
        String storageName = "files/" + UUID.randomUUID() + "_" + originalName; // 나중에 수정
        String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + storageName;

        // AWS S3에 파일 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        amazonS3Client.putObject(bucket, storageName, file.getInputStream(), metadata);

        return fileUrl;
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
    public void deleteFile(String url){
        String storagePath = url.substring(("https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/").length());
        amazonS3Client.deleteObject(bucket, storagePath);
    }
}
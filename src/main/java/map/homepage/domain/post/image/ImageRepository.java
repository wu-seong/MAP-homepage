package map.homepage.domain.post.image;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    // 이미지 정보 저장 메소드
    Image save(Image image);
}

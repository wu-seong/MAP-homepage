package map.homepage.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 조회
    List<Post> findAll();
    Optional<Post> findById(Long postId);
}


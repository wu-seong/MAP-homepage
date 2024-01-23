package map.homepage.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {


    List<Post> findAll(); // 게시글 목록 조회

    Optional<Post> findById(Long postId); // 단일 게시물 조회
    Post save(Post post); // 게시글 저장
}


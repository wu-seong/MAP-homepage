// PostRepositoy.java
package map.homepage.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByDtype(String dtype, Pageable pageable); // 게시글 디타입 구분해서 조회
    List<Post> findAllByIsNoticeTrue(Pageable pageable); // 공지 게시글 목록 조회
    Post save(Post post); // 게시글 저장

    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    @Transactional(readOnly = true)
    Optional<Post> findById(@Param("postId") Long postId); // postId로 단일 게시글 조회

    @Modifying
    @Query("UPDATE Post p SET p.views = p.views + 1 WHERE p.id = :postId")
    @Transactional
    void incrementViews(@Param("postId") Long postId); // 조회수 증가
}
package map.homepage.domain.post;

import map.homepage.domain.post.dto.PostRequestDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 게시글 조회
    List<Post> findAll();

//    // 게시글 추가
//    @Modifying
//    @Query("INSERT INTO Post (created_at, member_member_id, updated_at, content, dtype, role, title) " +
//            "VALUES (:createdAt, :memberId, :updatedAt, :content, :dtype, :role, :title)")
//    void addPost(@Param("createdAt") LocalDateTime createdAt,
//                 @Param("memberId") Long memberId,
//                 @Param("updatedAt") LocalDateTime updatedAt,
//                 @Param("content") String content,
//                 @Param("dtype") String dtype,
//                 @Param("role") String role,
//                 @Param("title") String title);
//
//    // 게시글 수정
//    @Modifying
//    @Query("UPDATE Post p SET p.title = :title, p.content = :content, p.updatedAt = :updatedAt " +
//            "WHERE p.id = :postId")
//    void updatePost(@Param("postId") Long postId,
//                    @Param("title") String title,
//                    @Param("content") String content,
//                    @Param("updatedAt") LocalDateTime updatedAt);
//
//    // 게시글 삭제
//    @Modifying
//    @Query("DELETE FROM Post p WHERE p.id = :postId")
//    void deletePostById(@Param("postId") Long postId);
}


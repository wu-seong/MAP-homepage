package map.homepage.domain.post.comment;

import map.homepage.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 데이터 베이스를 왔다리 갔다리 하면서 정보를 Service한테 주고 받는 역할
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostId(Long post_id);
}

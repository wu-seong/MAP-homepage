package map.homepage.domain.post.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

// 데이터 베이스를 왔다리 갔다리 하면서 정보를 Service한테 주고 받는 역할
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByPostId(Long post_id, PageRequest pageRequest);
}

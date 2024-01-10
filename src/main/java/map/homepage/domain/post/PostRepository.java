package map.homepage.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //DB와 연결 -> 우성이형 끝나면
}


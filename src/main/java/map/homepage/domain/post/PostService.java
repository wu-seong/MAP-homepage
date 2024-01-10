package map.homepage.domain.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService { // 비즈니스 로직 처리 (예외 처리)
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 비즈니스 로직 메서드들
}


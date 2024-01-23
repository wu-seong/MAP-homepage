package map.homepage.domain.post;

import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // 게시글 목록 조회
    @Override
    public List<PostResponseDTO> getPostList() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 단일 게시글 조회
    @Override
    @Transactional
    public PostResponseDTO viewPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // 조회 수 증가
            post.setViews(post.getViews() + 1);

            return PostResponseDTO.fromEntity(post);
        } else {
            throw new PostNotFoundException("삭제된 게시글입니다 :" + postId);
        }
    }
}

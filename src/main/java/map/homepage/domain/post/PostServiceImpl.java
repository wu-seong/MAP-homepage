package map.homepage.domain.post;

import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<PostResponseDTO> getPostList() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Override
//    public PostResponseDTO getPostDetails(Long postId) {
//        Optional<Post> optionalPost = postRepository.findById(postId);
//        return optionalPost.map(PostResponseDTO::fromEntity).orElse(null);
//    }
//
//    @Override
//    public Long createPost(PostRequestDTO postRequestDTO) {
//        Post post = new Post();
//        Post savedPost = postRepository.save(post);
//        return savedPost.getId();
//    }
//
//    @Override
//    public boolean updatePost(Long postId, PostRequestDTO postRequestDTO) {
//        Optional<Post> optionalPost = postRepository.findById(postId);
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            post.setTitle(postRequestDTO.getTitle());
//            post.setContent(postRequestDTO.getContent());
//            postRepository.save(post);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deletePost(Long postId) {
//        if (postRepository.existsById(postId)) {
//            postRepository.deleteById(postId);
//            return true;
//        }
//        return false;
//    }
}

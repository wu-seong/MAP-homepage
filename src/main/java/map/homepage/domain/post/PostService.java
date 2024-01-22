package map.homepage.domain.post;

import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.dto.PostRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public interface PostService {

    List<PostResponseDTO> getPostList();

//    PostResponseDTO getPostDetails(Long postId);
//
//    Long createPost(PostRequestDTO postRequestDTO);
//
//    boolean updatePost(Long postId, PostRequestDTO postRequestDTO);
//
//    boolean deletePost(Long postId);
}


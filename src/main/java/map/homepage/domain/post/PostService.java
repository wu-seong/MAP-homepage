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

    List<PostResponseDTO> getPostList(); // 게시글 목록 조회
    PostResponseDTO viewPost(Long postId); // 단일 게시글 조회
    PostResponseDTO addPost(Long memberId, PostRequestDTO postRequestDTO); // 게시글 추가
}


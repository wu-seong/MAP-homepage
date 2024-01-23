package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<PostResponseDTO>> getPostList() {
        List<PostResponseDTO> postList = postService.getPostList();
        return ResponseEntity.ok(postList);
    }

    // 단일 게시글 조회
    @GetMapping("/{postId}/view")
    public PostResponseDTO viewPost(@PathVariable Long postId) {
        return postService.viewPost(postId);
    }

    // 게시글 추가
    @PostMapping("/{memberId}/add")
    public PostResponseDTO addPost(
            @PathVariable Long memberId,
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        return postService.addPost(memberId, postRequestDTO);
    }
}


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
@RequestMapping("/posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회
    @GetMapping("/")
    public ResponseEntity<List<PostResponseDTO>> getPostList() {
        List<PostResponseDTO> postList = postService.getPostList();
        return ResponseEntity.ok(postList);
    }

    // 단일 게시글 조회
    @GetMapping("/{postId}")
    public PostResponseDTO viewPost(@PathVariable Long postId) {
        return postService.viewPost(postId);
    }

    // 게시글 추가
    @PostMapping("/{memberId}")
    public PostResponseDTO createPost(
            @PathVariable Long memberId,
            @RequestBody PostRequestDTO postRequestDTO
    ) { return postService.createPost(memberId, postRequestDTO);
    }

    // 게시글 수정
    @PutMapping("/{memberId}/{postId}")
    public PostResponseDTO updatePost(
            @PathVariable Long memberId,
            @PathVariable Long postId,
            @RequestBody PostRequestDTO postRequestDTO
    ) { return postService.updatePost(memberId, postId, postRequestDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{memberId}/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long memberId,
            @PathVariable Long postId
    ) { postService.deletePost(memberId, postId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}


// PostController.java
package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;

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
    @GetMapping("")
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
    @PostMapping("")
    public PostResponseDTO createPost(
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        Member member = MemberContext.getMember();
        return postService.createPost(member,postRequestDTO);
    }

    // 게시글 수정
    @PutMapping("/{postId}")
    public PostResponseDTO updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        Member member = MemberContext.getMember();
        return postService.updatePost(member, postId, postRequestDTO);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long postId
    ) {
        Member member = MemberContext.getMember();
        postService.deletePost(member, postId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}


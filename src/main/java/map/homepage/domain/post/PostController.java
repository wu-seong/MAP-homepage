// PostController.java
package map.homepage.domain.post;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//http://localhost:8080/swagger-ui/index.html
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 사진 게시글 목록 조회
    @GetMapping("/photo")
    public ResponseEntity<List<PostResponseDTO>> getPhotoPostList() {
        List<PostResponseDTO> postList = postService.getPhotoPostList();
        return ResponseEntity.ok(postList);
    }

    // 일반 게시글 목록 조회
    @GetMapping("/general")
    public ResponseEntity<List<PostResponseDTO>> getGeneralPostList() {
        List<PostResponseDTO> postList = postService.getGeneralPostList();
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
        return postService.createPost(member, postRequestDTO);
    }

    // 사진 게시글 추가
    @PostMapping(value = "/withImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDTO createImagePost(
            @RequestPart(name = "file") List<MultipartFile> file,
            @RequestPart(name = "postRequestDTO") PostRequestDTO postRequestDTO
    ) throws IOException {
        Member member = MemberContext.getMember();
        return postService.createImagePost(member, file, postRequestDTO);
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


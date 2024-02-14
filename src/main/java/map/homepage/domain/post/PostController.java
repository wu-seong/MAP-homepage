// PostController.java
package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.Operation;
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

    @GetMapping("/photo")
    @Operation(summary = "사진 게시글 조회 API")
    public ResponseEntity<List<PostResponseDTO>> getPhotoPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponseDTO> photoPostList = postService.getPhotoPostList(page, size);
        return ResponseEntity.ok(photoPostList);
    }

    @GetMapping("/general")
    @Operation(summary = "일반 게시글 조회 API")
    public ResponseEntity<List<PostResponseDTO>> getGeneralPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponseDTO> generalPostList = postService.getGeneralPostList(page, size);
        return ResponseEntity.ok(generalPostList);
    }

    @GetMapping("/notice")
    @Operation(summary = "공지 게시글 조회 API")
    public ResponseEntity<List<PostResponseDTO>> getNoticePostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<PostResponseDTO> noticePostList = postService.getNoticePostList(page, size);
        return ResponseEntity.ok(noticePostList);
    }

    @GetMapping("/{post-id}")
    @Operation(summary = "단일 게시글 조회 API")
    public PostResponseDTO viewPost(
            @PathVariable("post-id") Long postId
    ) {
        return postService.viewPost(postId);
    }

    @PostMapping("")
    @Operation(summary = "일반 게시글 작성 API")
    public PostResponseDTO createPost(
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        Member member = MemberContext.getMember();
        return postService.createPost(member, postRequestDTO);
    }

    @PostMapping(value = "/withImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "사진 게시글 작성 API")
    public PostResponseDTO createImagePost(
            @RequestPart(name = "file") List<MultipartFile> file,
            @RequestPart(name = "postRequestDTO") PostRequestDTO postRequestDTO
    ) throws IOException {
        Member member = MemberContext.getMember();
        return postService.createImagePost(member, file, postRequestDTO);
    }

    @PutMapping("/{post-id}")
    @Operation(summary = "게시글 수정 API")
    public PostResponseDTO updatePost(
            @PathVariable("post-id") Long postId,
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        Member member = MemberContext.getMember();
        return postService.updatePost(member, postId, postRequestDTO);
    }

    @PatchMapping("/notice/{post-id}")
    @Operation(summary = "게시글 공지 등록 또는 해제 API")
    public ResponseEntity<Void> toggleNotice(
            @PathVariable("post-id") Long postId
    ) {
        Member member = MemberContext.getMember();
        postService.toggleNotice(member, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{post-id}")
    @Operation(summary = "게시글 삭제 API")
    public ResponseEntity<String> deletePost(
            @PathVariable("post-id") Long postId
    ) {
        Member member = MemberContext.getMember();
        postService.deletePost(member, postId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}

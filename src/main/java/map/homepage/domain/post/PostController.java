// PostController.java
package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.converter.PostConverter;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.dto.PostResponseListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//http://localhost:8080/swagger-ui/index.html
//http://localhost:8080/oauth2/authorize/kakao
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
    @Operation(summary = "사진 게시글 목록 조회 API")
    public ApiResponse<PostResponseListDTO> getPhotoPosList(
            @RequestParam(defaultValue = "1") int page
    ) {
        int size = 15;
        Page<PostResponseDTO> photoPostPage = postService.getPhotoPostPage(page, size);
        PostResponseListDTO postResponseListDTO = PostConverter.toPostResponseListDTO(photoPostPage,page);
        return ApiResponse.onSuccess(postResponseListDTO);
    }


    @GetMapping("/general")
    @Operation(summary = "일반 게시글 목록 조회 API")
    public ApiResponse<PostResponseListDTO> getGeneralPostList(
            @RequestParam(defaultValue = "1") int page
    ) {
        int size = 10;
        Page<PostResponseDTO> photoPostPage = postService.getGeneralPostPage(page, size);
        PostResponseListDTO postResponseListDTO = PostConverter.toPostResponseListDTO(photoPostPage,page);
        return ApiResponse.onSuccess(postResponseListDTO);
    }

    @GetMapping("/notice")
    @Operation(summary = "공지 게시글 목록 조회 API")
    public ApiResponse<List<PostResponseDTO>> getNoticePostList(
            @RequestParam(defaultValue = "0") int page
    ) {
        int size = 5;
        List<PostResponseDTO> noticePostList = postService.getNoticePostList(page, size);
        return ApiResponse.onSuccess(noticePostList);
    }

    @GetMapping("/{post-id}")
    @Operation(summary = "단일 게시글 조회 API")
    public ApiResponse<PostResponseDTO> viewPost(
            @PathVariable("post-id") Long postId
    ) {
        PostResponseDTO viewedPost = postService.viewPost(postId);
        return ApiResponse.onSuccess(viewedPost);
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
            //produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "일반 게시글 작성 API")
    public ApiResponse<PostResponseDTO> createPost(
            @RequestPart(name = "postRequestDTO") @Valid PostRequestDTO postRequestDTO,
            @RequestPart(name = "file", required = false) MultipartFile file
    ){
        Member member = MemberContext.getMember();
        PostResponseDTO createdPost = postService.createPost(member, file, postRequestDTO);
        return ApiResponse.onSuccess(createdPost);
    }


    @PostMapping(value = "/withImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.APPLICATION_JSON_VALUE})
                //produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "사진 게시글 작성 API")
    public ApiResponse<PostResponseDTO> createImagePost(
            @RequestPart(name = "postRequestDTO") @Valid PostRequestDTO postRequestDTO,
            @RequestPart(name = "file") List<MultipartFile> file
    ) throws IOException {
        Member member = MemberContext.getMember();
        PostResponseDTO createdPost = postService.createImagePost(member, file, postRequestDTO);
        return ApiResponse.onSuccess(createdPost);
    }

    @PutMapping("/{post-id}")
    @Operation(summary = "게시글 수정 API")
    public ApiResponse<PostResponseDTO> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestBody PostRequestDTO postRequestDTO
    ) {
        Member member = MemberContext.getMember();
        PostResponseDTO updatedPost = postService.updatePost(member, postId, postRequestDTO);
        return ApiResponse.onSuccess(updatedPost);
    }

    @PatchMapping("/notice/{post-id}")
    @Operation(summary = "게시글 공지 등록 또는 해제 API")
    public ApiResponse<String> toggleNotice(
            @PathVariable("post-id") Long postId
    ) {
        Member member = MemberContext.getMember();
        postService.toggleNotice(member, postId);
        return ApiResponse.onSuccess("성공적으로 변경되었습니다.");
    }

    @DeleteMapping("/{post-id}")
    @Operation(summary = "게시글 삭제 API")
    public ApiResponse<String> deletePost(
            @PathVariable("post-id") Long postId
    ) {
        Member member = MemberContext.getMember();
        postService.deletePost(member, postId);
        return ApiResponse.onSuccess("성공적으로 삭제되었습니다.");
    }
}

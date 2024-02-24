// PostController.java
package map.homepage.domain.post;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.ApiResponse;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.comment.Comment;
import map.homepage.domain.post.comment.CommentService;
import map.homepage.domain.post.converter.PostConverter;
import map.homepage.domain.post.dto.general.GeneralPostPreviewResponseListDTO;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.general.GeneralPostResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseListDTO;
import map.homepage.domain.post.dto.photo.PhotoPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

//http://localhost:8080/swagger-ui/index.html
//http://localhost:8080/oauth2/authorize/kakao
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService  postService;
    private final CommentService commentService;

    @GetMapping("/photo")
    @Operation(summary = "사진 게시글 목록 조회 API")
    public ApiResponse<PhotoPostPreviewResponseListDTO> getPhotoPosList(
            @RequestParam(defaultValue = "1") int page
    ) {
        int size = 15;
        Page<Post> photoPostPage = postService.getPhotoPostPage(page, size);
        return ApiResponse.onSuccess(PostConverter.toPhotoPostPreviewResponseListDTO(photoPostPage,page));
    }


    @GetMapping("/general")
    @Operation(summary = "일반 게시글 목록 조회 API")
    public ApiResponse<GeneralPostPreviewResponseListDTO> getGeneralPostList(
            @RequestParam(defaultValue = "1") int page
    ) {
        int size = 10;
        Page<Post> generalPostPage = postService.getGeneralPostPage(page, size);
        return ApiResponse.onSuccess(PostConverter.toPostPreviewResponseListDTO(generalPostPage, page));
    }

    @GetMapping("/general/notice")
    @Operation(summary = "공지 게시글 목록 조회 API")
    public ApiResponse<GeneralPostPreviewResponseListDTO> getNoticePostList(
            @RequestParam(defaultValue = "0") int page
    ) {
        int size = 5;
        List<Post> noticePostList = postService.getNoticePostList(page, size);
        return ApiResponse.onSuccess(PostConverter
                .toNotificationPostPreviewResponseListDTO(noticePostList));
    }

    @GetMapping("/general/{post-id}")
    @Operation(summary = "일반 게시글 단일 조회 API")
    public ApiResponse<GeneralPostResponseDTO> viewGeneralPost(
            @PathVariable("post-id") Long postId
    ) {
        Post post = postService.viewPost(postId);
        Page<Comment> comment = commentService.getComment(postId, 0);
        int totalComment = comment.getTotalPages();

        return ApiResponse.onSuccess(PostConverter.fromEntityToDetail(post, totalComment));
    }
    @GetMapping("/photo/{post-id}")
    @Operation(summary = "사진 게시글 단일 조회 API")
    public ApiResponse<PhotoPostResponseDTO> viewImagePost(
            @PathVariable("post-id") Long postId
    ) {
        Post post = postService.viewPost(postId);
        Page<Comment> comment = commentService.getComment(postId, 0);
        int totalComment = comment.getTotalPages();
        return ApiResponse.onSuccess(PostConverter.fromEntityToImageDetail(post, totalComment));
    }
    @PostMapping(value = "/general", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "일반 게시글 작성 API")
    public ApiResponse<GeneralPostResponseDTO> createPost(
            @RequestPart(name = "postRequestDTO") @Valid PostRequestDTO postRequestDTO,
            @RequestPart(name = "file", required = false) MultipartFile file
    ){
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(PostConverter
                .fromEntityToDetail(postService.createPost(member, file, postRequestDTO), 0) );
    }

    @PostMapping(value = "/photo", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "사진 게시글 작성 API")
    public ApiResponse<PhotoPostResponseDTO> createImagePost(
            @RequestPart(name = "postRequestDTO") @Valid PostRequestDTO postRequestDTO,
            @RequestPart(name = "file") List<MultipartFile> file
    ) throws IOException {
        Member member = MemberContext.getMember();
        return ApiResponse.onSuccess(PostConverter
                .fromEntityToImageDetail(postService.createImagePost(member, file, postRequestDTO), 0) );
    }

    @PutMapping(value = "/{post-id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE} )
    @Operation(summary = "게시글 수정 API")
    public ApiResponse<GeneralPostResponseDTO> updatePost(
            @PathVariable("post-id") Long postId,
            @RequestPart(name = "postRequestDTO") @Valid PostRequestDTO postRequestDTO,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) {
        Member member = MemberContext.getMember();
        Page<Comment> comment = commentService.getComment(postId, 0);
        int totalComment = comment.getTotalPages();
        return ApiResponse.onSuccess(PostConverter
                .fromEntityToDetail(postService.updatePost(member, file ,postId, postRequestDTO), totalComment ) );
    }

    @PatchMapping("/general/{post-id}/notice")
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

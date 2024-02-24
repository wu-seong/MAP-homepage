// PostService.java
package map.homepage.domain.post;

import map.homepage.domain.member.Member;
import map.homepage.domain.post.dto.general.GeneralPostPreviewResponseDTO;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.general.GeneralPostResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {

    Page<Post> getPhotoPostPage(int page, int size); // 사진 게시글 목록 조회
    Page<Post> getGeneralPostPage(int page, int size); // 일반 게시글 목록 조회
    List<Post> getNoticePostList(int page, int size); // 공지 게시글 목록 조회
    Post viewPost(Long postId); // 단일 게시글 조회
    Post createPost(Member member, MultipartFile file, PostRequestDTO postRequestDTO); // 게시글 추가
    Post createImagePost(Member member, List<MultipartFile> file, PostRequestDTO postRequestDTO) throws IOException; // 사진 게시글 추가
    Post updatePost(Member member, MultipartFile file, Long postId, PostRequestDTO postRequestDTO); // 게시글 수정
    void deletePost(Member member, Long postId); // 게시글 삭제
    void toggleNotice(Member member, Long postId); // 게시글 공지 등록 또는 해제

    boolean isAuthorOrAdmin(Member member, Post post); // 권한 확인
    boolean isImageFile(MultipartFile fil); // 이미지 확인
}
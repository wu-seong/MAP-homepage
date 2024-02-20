// PostService.java
package map.homepage.domain.post;

import map.homepage.domain.member.Member;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {

    Page<PostResponseDTO> getPhotoPostPage(int page, int size); // 사진 게시글 목록 조회
    Page<PostResponseDTO> getGeneralPostPage(int page, int size); // 일반 게시글 목록 조회
    List<PostResponseDTO> getNoticePostList(int page, int size); // 공지 게시글 목록 조회
    PostResponseDTO viewPost(Long postId); // 단일 게시글 조회
    PostResponseDTO createPost(Member member, MultipartFile file, PostRequestDTO postRequestDTO); // 게시글 추가
    PostResponseDTO createImagePost(Member member, List<MultipartFile> file, PostRequestDTO postRequestDTO) throws IOException; // 사진 게시글 추가
    PostResponseDTO updatePost(Member member, Long postId, PostRequestDTO postRequestDTO); // 게시글 수정
    void deletePost(Member member, Long postId); // 게시글 삭제
    void toggleNotice(Member member, Long postId); // 게시글 공지 등록 또는 해제
}
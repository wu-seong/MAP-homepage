// PostService.java
package map.homepage.domain.post;

import map.homepage.domain.member.Member;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PostService {

    List<PostResponseDTO> getPhotoPostList(); // 사진 게시글 목록 조회
    List<PostResponseDTO> getGeneralPostList(); // 사진 게시글 목록 조회

    PostResponseDTO viewPost(Long postId); // 단일 게시글 조회
    PostResponseDTO createPost(Member member, PostRequestDTO postRequestDTO); // 게시글 추가
    PostResponseDTO createImagePost(Member member, List<MultipartFile> file, PostRequestDTO postRequestDTO) throws IOException; // 사진 게시글 추가
    PostResponseDTO updatePost(Member member, Long postId, PostRequestDTO postRequestDTO); // 게시글 수정
    void deletePost(Member member, Long postId); // 게시글 삭제
    boolean isAuthorOrAdmin(Member member, Post post); //권한 확인
}
// PostServiceImpl.java
package map.homepage.domain.post;

import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.ImageService;
import map.homepage.exception.PostNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    private final MemberRepository memberRepository;

    // 게시글 목록 조회
    @Override
    public List<PostResponseDTO> getPostList() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 단일 게시글 조회
    @Override
    @Transactional
    public PostResponseDTO viewPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setViews(post.getViews() + 1); // 조회 수 증가
            return PostResponseDTO.fromEntity(post);
        } else {
            throw new PostNotFoundException("게시글을 찾을 수 없습니다.");
        }
    }

    // 게시물 추가
    @Transactional
    public PostResponseDTO createPost(Member member, PostRequestDTO postRequestDTO) {

        Post post = new Post();
        post.setMember(member);
        post.setViews(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setContent(postRequestDTO.getContent());
        post.setDtype(postRequestDTO.getDtype());
        post.setTitle(postRequestDTO.getTitle());
        post.setRole(member.getRole());

        postRepository.save(post);
        return PostResponseDTO.fromEntity(post);
    }

    // 게시글 수정
    @Override
    @Transactional
    public PostResponseDTO updatePost(Member member, Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        // 현재 사용자의 memberId와 게시글의 작성자의 memberId를 비교하거나 ADMIN 권한 확인
        if (!isAuthorOrAdmin(member, post)) {
            try {
                throw new AccessDeniedException("권한이 없습니다.");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }

        post.setTitle(postRequestDTO.getTitle()); // 제목
        post.setDtype(postRequestDTO.getDtype()); // 종류
        post.setContent(postRequestDTO.getContent()); // 내용

        post.setUpdatedAt(LocalDateTime.now()); // 수정 시간
        postRepository.save(post);
        return PostResponseDTO.fromEntity(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("게시글을 찾을 수 없습니다."));

        // 현재 사용자의 memberId와 게시글의 작성자의 memberId를 비교하거나 ADMIN 권한 확인
        if (!isAuthorOrAdmin(member, post)) {
            try {
                throw new AccessDeniedException("권한이 없습니다.");
            } catch (AccessDeniedException e) {
                throw new RuntimeException(e);
            }
        }
        List<Image> images = post.getImages(); // 해당 게시글의 사진도 삭제
        for (Image image : images) {
            imageService.deleteImage(image.getId());
        }

        postRepository.delete(post);
    }

    public boolean isAuthorOrAdmin(Member member, Post post) {
        // 현재 사용자의 memberId와 게시글의 작성자의 memberId를 비교하거나 ADMIN 권한 확인
        return member.getId().equals(post.getMember().getId()) || member.isAdmin();
    }
}
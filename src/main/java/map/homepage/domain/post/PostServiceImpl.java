// PostServiceImpl.java
package map.homepage.domain.post;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.PostResponseDTO;
import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.ImageService;
import map.homepage.exception.GeneralException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    // 사진 게시글 목록 조회
    @Override
    public List<PostResponseDTO> getPhotoPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> photoPosts = postRepository.findByDtype("photo", pageable);
        return photoPosts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 일반 게시글 목록 조회
    @Override
    public List<PostResponseDTO> getGeneralPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> generalPosts = postRepository.findByDtype("general", pageable);
        return generalPosts.stream()
                .map(PostResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 공지 게시글 목록 조회
    @Override
    public List<PostResponseDTO> getNoticePostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Post> noticePosts = postRepository.findAllByIsNoticeTrue(pageable);
        return noticePosts.stream()
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
            postRepository.incrementViews(postId);
            return PostResponseDTO.fromEntity(post);
        } else {
            throw new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND);
        }
    }

    // 일반 게시물 작성
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

    // 사진 게시글 작성
    @Transactional
    public PostResponseDTO createImagePost(Member member, List<MultipartFile> file, PostRequestDTO postRequestDTO) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new GeneralException(ErrorStatus.IMAGE_NOT_FOUND);
        }

        for (MultipartFile f : file) {
            if (!isImageFile(f)) {
                throw new GeneralException(ErrorStatus.IS_NOT_IMAGE);
            }
        }

        Post post = new Post();
        post.setMember(member);
        post.setViews(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setContent(postRequestDTO.getContent());
        post.setDtype(postRequestDTO.getDtype());
        post.setTitle(postRequestDTO.getTitle());
        post.setRole(member.getRole());

        postRepository.save(post); // 게시글 저장
        Long postId = post.getId();

        List<String> thumbNails = new ArrayList<>();
        for (MultipartFile f : file) {
            String URL = imageService.uploadImage(postId,f);
            thumbNails.add(URL); // 넘어오는 썸네일 URL을 저장
        }

        post.setThumbnail(thumbNails.get(0)); // 첫번째 사진을 post에 저장
        return PostResponseDTO.fromEntity(post);
    }

    // 게시글 수정
    @Override
    @Transactional
    public PostResponseDTO updatePost(Member member, Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        if (!isAuthorOrAdmin(member, post)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        post.setTitle(postRequestDTO.getTitle());
        post.setDtype(postRequestDTO.getDtype());
        post.setContent(postRequestDTO.getContent());

        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
        return PostResponseDTO.fromEntity(post);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        if (!isAuthorOrAdmin(member, post)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        List<Image> images = post.getImages(); // 해당 게시글의 사진도 삭제
        for (Image image : images) {
            imageService.deleteImage(image.getId());
        }

        postRepository.delete(post);
    }

    // 게시글 공지 등록 또는 해제
    public void toggleNotice(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        if (!member.isAdmin()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        post.setNotice(!post.isNotice());
        postRepository.save(post);
    }

    // 권한 확인
    public boolean isAuthorOrAdmin(Member member, Post post) {
        // 현재 사용자의 memberId와 게시글의 작성자의 memberId를 비교하거나 ADMIN 권한 확인
        return member.getId().equals(post.getMember().getId()) || member.isAdmin();
    }

    // 파일 형식 확인
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image");
    }
}
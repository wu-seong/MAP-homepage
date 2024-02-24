// PostServiceImpl.java
package map.homepage.domain.post;

import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.post.attachedFile.AttachedFile;
import map.homepage.domain.post.converter.PostConverter;
import map.homepage.domain.post.dto.PostRequestDTO;
import map.homepage.domain.post.dto.general.GeneralPostResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostResponseDTO;
import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.FileManagementService;
import map.homepage.exception.GeneralException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileManagementService fileManagementService;


    // 사진 게시글 목록 조회
    @Override
    public Page<Post> getPhotoPostPage(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return postRepository.findByDtype("photo", pageable);
    }

    // 일반 게시글 목록 조회
    @Override
    public Page<Post> getGeneralPostPage(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return postRepository.findByDtype("general", pageable);
    }

    // 공지 게시글 목록 조회
    @Override
    public List<Post> getNoticePostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return postRepository.findAllByIsNoticeTrue(pageable);

    }

    // 단일 게시글 조회
    @Override
    @Transactional
    public Post viewPost(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            postRepository.incrementViews(postId);
            return post;
        } else {
            throw new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND);
        }
    }

    // 일반 게시물 작성
    @Transactional
    public Post createPost(Member member, MultipartFile file, PostRequestDTO postRequestDTO) {

        Post post = new Post();
        post.setMember(member);
        post.setViews(0);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(null);
        post.setContent(postRequestDTO.getContent());
        post.setDtype(postRequestDTO.getDtype());
        post.setTitle(postRequestDTO.getTitle());
        post.setRole(member.getRole());


        if (file != null && !file.isEmpty()) {
            try {
                AttachedFile attachedFile = fileManagementService.uploadFile(file);
                post.setAttachedFile(attachedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        postRepository.save(post);
        return post;
    }

    // 사진 게시글 작성
    @Transactional
    @Override
    public Post createImagePost(Member member, List<MultipartFile> file, PostRequestDTO postRequestDTO) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new GeneralException(ErrorStatus.IMAGE_NOT_FOUND);
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

        boolean isFirst = true;
        for (MultipartFile f : file) {
            Image image = fileManagementService.uploadImage(postId, f);
            if(isFirst){
                post.setThumbnail(image.getImageUrl());
                isFirst = false;
            }
        }
        return post;
    }

    // 게시글 수정
    @Override
    @Transactional
    public Post updatePost(Member member, MultipartFile file, Long postId, PostRequestDTO postRequestDTO) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        if (!isAuthorOrAdmin(member, post)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 첨부파일 수정 시
        if (file != null && !file.isEmpty()) {
            try {
                // 업로드 파일 삭제
                AttachedFile olderAttachedFile = post.getAttachedFile();
                if (olderAttachedFile != null) {
                    fileManagementService.deleteFile(olderAttachedFile);
                }
                // 새로운 파일 업로드
                AttachedFile attachedFile = fileManagementService.uploadFile(file);
                post.setAttachedFile(attachedFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        post.setTitle(postRequestDTO.getTitle());
        post.setDtype(postRequestDTO.getDtype());
        post.setContent(postRequestDTO.getContent());

        return post;
    }

    // 게시글 삭제
    @Transactional
    @Override
    public void deletePost(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.ARTICLE_NOT_FOUND));

        // 사용자가 게시글 작성자거나 관리자인 경우만 삭제 가능
        if (!isAuthorOrAdmin(member, post)) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }

        // 일반 게시글에 첨부 파일이 있다면 삭제
        AttachedFile attachedFile = post.getAttachedFile();
        if (attachedFile != null) {
            fileManagementService.deleteFile(attachedFile);
        }
        // 사진 게시글이라면 사진 삭제
        List<Image> images = post.getImages();
        if(!images.isEmpty()){
            for (Image image : images) {
                fileManagementService.deleteImage(image.getId());
            }
        }
        postRepository.delete(post);
    }

    @Override
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
    @Override
    public boolean isAuthorOrAdmin(Member member, Post post) {
        // 현재 사용자의 memberId와 게시글의 작성자의 memberId를 비교하거나 ADMIN 권한 확인
        return member.getId().equals(post.getMember().getId()) || member.isAdmin();
    }

    // 파일 형식 확인
    @Override
    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image");
    }
}
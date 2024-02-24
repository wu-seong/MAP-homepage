package map.homepage.domain.post.converter;

import map.homepage.domain.post.Post;
import map.homepage.domain.post.attachedFile.AttachedFile;
import map.homepage.domain.post.attachedFile.converter.AttachedFileConverter;
import map.homepage.domain.post.attachedFile.dto.AttachedFileResponseDTO;
import map.homepage.domain.post.dto.general.GeneralPostPreviewResponseDTO;
import map.homepage.domain.post.dto.general.GeneralPostPreviewResponseListDTO;
import map.homepage.domain.post.dto.general.GeneralPostResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseDTO;
import map.homepage.domain.post.dto.photo.PhotoPostPreviewResponseListDTO;
import map.homepage.domain.post.dto.photo.PhotoPostResponseDTO;
import map.homepage.domain.post.image.Image;
import map.homepage.domain.post.image.converter.ImageConverter;
import map.homepage.domain.post.image.dto.ImageResponseDTO;
import map.homepage.domain.post.image.dto.ImageResponseListDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PostConverter {
    public static GeneralPostPreviewResponseListDTO toPostPreviewResponseListDTO(Page<Post> postPage, int page) {
        List<GeneralPostPreviewResponseDTO> generalPostPreviewResponseDTOList = postPage.stream()
                .map(PostConverter::fromEntityToPreview).collect(Collectors.toList());
        return GeneralPostPreviewResponseListDTO.builder()
                .postResponseDTOList(generalPostPreviewResponseDTOList)
                .listSize(postPage.getNumberOfElements())
                .totalPage(postPage.getTotalPages())
                .totalElements(postPage.getTotalElements())
                .isFirst(postPage.isFirst())
                .isLast(postPage.isLast())
                .nowPage(page)
                .build();
    }
    public static GeneralPostPreviewResponseListDTO toNotificationPostPreviewResponseListDTO(List<Post> postList) {
        List<GeneralPostPreviewResponseDTO> generalPostPreviewResponseDTOList = postList.stream()
                .map(PostConverter::fromEntityToPreview).collect(Collectors.toList());
        return GeneralPostPreviewResponseListDTO.builder()
                .postResponseDTOList(generalPostPreviewResponseDTOList)
                .listSize(postList.size())
                .build();
    }
    public static PhotoPostPreviewResponseListDTO toPhotoPostPreviewResponseListDTO(Page<Post> photoPostPage, int page) {
        photoPostPage.stream()
                .map(PostConverter::fromEntityToImagePreview).collect(Collectors.toList());
        return PhotoPostPreviewResponseListDTO.builder()
                .listSize(photoPostPage.getNumberOfElements())
                .totalPage(photoPostPage.getTotalPages())
                .totalElements(photoPostPage.getTotalElements())
                .isFirst(photoPostPage.isFirst())
                .isLast(photoPostPage.isLast())
                .nowPage(page)
                .build();
    }

    public static GeneralPostResponseDTO fromEntityToDetail(Post post, int totalComment) {
        AttachedFileResponseDTO attachedFileResponseDTO = null;
        AttachedFile attachedFile = post.getAttachedFile();
        if (attachedFile != null){ // 첨부파일이 존재 하면
            attachedFileResponseDTO = AttachedFileConverter.fromEntity(attachedFile);
        }
        return GeneralPostResponseDTO.builder()
                .postId(post.getId())
                .views(post.getViews())
                .title(post.getTitle())
                .content(post.getContent())
                .role(post.getRole())
                .createdAt(post.getCreatedAt())
                .writerName(post.getMember().getName())
                .writerId(post.getMember().getId())
                .isNotice(post.isNotice())
                .attachedFileResponseDTO(attachedFileResponseDTO)
                .totalComment(totalComment)
                .build();

    }
    public static GeneralPostPreviewResponseDTO fromEntityToPreview(Post post) {
        return GeneralPostPreviewResponseDTO.builder()
                .postId(post.getId())
                .view(post.getViews())
                .title(post.getTitle())
                .writerName(post.getMember().getName())
                .writerId(post.getMember().getId())
                .notice(post.isNotice())
                .uploadedTime(post.getCreatedAt())
                .build();
    }
    public static PhotoPostPreviewResponseDTO fromEntityToImagePreview(Post post) {
        return PhotoPostPreviewResponseDTO.builder()
                .postId(post.getId())
                .view(post.getViews())
                .title(post.getTitle())
                .writerName(post.getMember().getName())
                .writerId(post.getMember().getId())
                .notice(post.isNotice())
                .uploadedTime(post.getCreatedAt())
                .thumbnail(post.getThumbnail())
                .build();
    }
    public static PhotoPostResponseDTO fromEntityToImageDetail(Post post, int totalComment) {
        List<Image> images = post.getImages();
        List<ImageResponseDTO> imageResponseDTOList = images.stream()
                .map(ImageConverter::fromEntity).collect(Collectors.toList());
        ImageResponseListDTO responseDTO = ImageConverter.toImageResponseListDTO(imageResponseDTOList);
        return PhotoPostResponseDTO.builder()
                .imageResponseListDTO(responseDTO)
                .postId(post.getId())
                .views(post.getViews())
                .title(post.getTitle())
                .content(post.getContent())
                .role(post.getRole())
                .createdAt(post.getCreatedAt())
                .writerName(post.getMember().getName())
                .writerId(post.getMember().getId())
                .totalComment(totalComment)
                .build();
    }

}

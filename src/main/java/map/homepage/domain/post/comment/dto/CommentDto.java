package map.homepage.domain.post.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class CommentDto {
    @Builder
    @Getter
    @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬
    @NoArgsConstructor
    public static class CommentDetailDTO {
        private Long commentId;
        private String writer;
        private String writerProfileURI;
        private Long memberId;
        private String content;
        private LocalDateTime createdAt;
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class CommentListDTO {
        private List<CommentDetailDTO> commentDetailDtoList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateCommentRequestDTO{
        @Size(max = 100)
        @NotNull
        @NotBlank
        private String content;
    }
}

package map.homepage.domain.post.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentDto {
    @Builder
    @Getter
    @AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듬
    @NoArgsConstructor
    public static class CommentDetailDTO {
        private Long commentId;
        private String writer;
        private Long memberId;
        private String content;
    }

    /* public static CommentDto toDto(Comment comment) {
        String writer = (comment.getMember() != null) ? comment.getMember().getName() : null;
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(writer)
                .build();
    }*/

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
}

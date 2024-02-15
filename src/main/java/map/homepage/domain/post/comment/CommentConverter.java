package map.homepage.domain.post.comment;

import map.homepage.domain.post.comment.dto.CommentDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static CommentDto.CommentDetailDTO toDto(Comment comment) {
        return CommentDto.CommentDetailDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(comment.getMember().getName())
                .build();
    }

    public static CommentDto.CommentListDTO commentListDto(Page<Comment> comment) {

        List<CommentDto.CommentDetailDTO> commentDtoList = comment.stream()
                .map(CommentConverter::toDto)
                .collect(Collectors.toList());

        return CommentDto.CommentListDTO.builder()
                .isLast(comment.isLast())
                .isFirst(comment.isLast())
                .totalPage(comment.getTotalPages())
                .listSize(commentDtoList.size())
                .commentDetailDtoList(commentDtoList)
                .build();
    }
}

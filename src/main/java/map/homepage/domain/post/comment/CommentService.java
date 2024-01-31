package map.homepage.domain.post.comment;

import map.homepage.domain.post.comment.dto.CommentCreateRequest;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.domain.post.comment.dto.CommentReadCondition;

import java.util.List;

public interface CommentService {
    public List<CommentDto> getComment(CommentReadCondition condition);

    public Comment writeComment(final CommentCreateRequest commentCreateRequest, Long postId);

    public Comment deleteComment(final Long commentId);

}

package map.homepage.domain.post.comment;

import map.homepage.domain.member.Member;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.domain.post.comment.dto.CommentReadCondition;

import java.util.List;

public interface CommentService {
    public List<CommentDto> getComment(CommentReadCondition condition);

    public Comment writeComment(final String content, Long postId);

    public Comment deleteComment(final Long commentId, Member member);

}

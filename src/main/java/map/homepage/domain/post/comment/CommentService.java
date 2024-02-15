package map.homepage.domain.post.comment;

import map.homepage.domain.member.Member;
import org.springframework.data.domain.Page;

public interface CommentService {
    public Page<Comment> getComment(Long postId, Integer page);

    public Comment writeComment(final String content, Long postId);

    public Comment deleteComment(final Long commentId, Member member);

}

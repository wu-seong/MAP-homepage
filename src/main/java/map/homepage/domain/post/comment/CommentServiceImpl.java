package map.homepage.domain.post.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import map.homepage.apiPayload.code.status.ErrorStatus;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.exception.GeneralException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Repository에게 받은 정보를 가공해서 Controller에게 주거나, Controller에게 받은 정보를 Repository한테 주는 역할
@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<CommentDto> getComment(Long postId) {
        // return 해주기 전에
        return commentRepository.findAllByPostId(postId).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Comment writeComment(final String content, Long postId) {
        Member member = MemberContext.getMember();
        Post post = postRepository.findById(postId).orElseThrow(()->new GeneralException(ErrorStatus.USER_NOT_FOUND));
        Comment comment = new Comment(content, member, post);
        return commentRepository.save(comment);
    }

    @Override
    public Comment deleteComment(final Long commentId, Member member) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new GeneralException(ErrorStatus.COMMENT_NOT_FOUND));
        validateOwnComment(comment, member);
        commentRepository.delete(comment);
        return comment;
    }

    private void validateOwnComment(Comment comment, Member member) {
        if (!member.getId().equals(comment.getMember().getId()) || !member.isAdmin()) {
            throw new GeneralException(ErrorStatus._FORBIDDEN);
        }
    }

}
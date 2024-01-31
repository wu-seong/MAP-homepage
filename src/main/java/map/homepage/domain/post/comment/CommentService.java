package map.homepage.domain.post.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.MemberRepository;
import map.homepage.domain.member.auth.MemberContext;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.comment.dto.CommentCreateRequest;
import map.homepage.domain.post.comment.dto.CommentDto;
import map.homepage.domain.post.comment.dto.CommentReadCondition;
import map.homepage.exception.PostNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Repository에게 받은 정보를 가공해서 Controller에게 주거나, Controller에게 받은 정보를 Repository한테 주는 역할
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public List<CommentDto> getComment(CommentReadCondition condition) {
        // return 해주기 전에
        return commentRepository.findAllByPostId(condition.getPostId()).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public Comment writeComment(final CommentCreateRequest commentCreateRequest, Long postId) {
        Member member = MemberContext.getMember();
        Post post = postRepository.findById(postId).orElseThrow(()->new PostNotFoundException("게시물을 찾을 수 없습니다."));
        Comment comment = new Comment(commentCreateRequest.getContent(), member, post);
        return commentRepository.save(comment);
    }

    @Transactional
    public Comment deleteComment(final Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new IllegalArgumentException("댓글 id를 찾을 수 없습니다."));
        Post post = postRepository.findById(comment.getPost().getId()).orElseThrow();
        commentRepository.delete(comment);
        return comment;
    }

    /* private void validateOwnComment(Comment comment, Member member) {
        if (!comment.isOwnMember(member.getId())) {
            throw new MemberNotEqualsException();
        }
    } */

}

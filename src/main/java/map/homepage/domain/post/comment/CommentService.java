package map.homepage.domain.post.comment;

import jakarta.transaction.Transactional;
import map.homepage.domain.post.PostRepository;
import map.homepage.domain.post.comment.Dto.CommentCreateRequest;
import map.homepage.domain.post.comment.Dto.CommentDto;
import map.homepage.domain.post.comment.Dto.CommentReadCondition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Repository에게 받은 정보를 가공해서 Controller에게 주거나, Controller에게 받은 정보를 Repository한테 주는 역할
// @RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    public CommentService(final CommentRepository commentRepository, final PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public List<CommentDto> getComment(final CommentReadCondition condition) {
        return commentRepository.findAllByPostId(condition.getPostId()).stream()
                .map(CommentDto::toDto)
                .collect(Collectors.toList());
    }

    // 도하 오빠 작업 후
    @Transactional
    public CommentDto writeComment(final CommentCreateRequest commentCreateRequest) {
       return null;
    }

    @Transactional
    public String deleteComment(final Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("댓글 id를 찾을 수 없습니다."));
        commentRepository.delete(comment);
        return "댓글 삭제";
    }

}

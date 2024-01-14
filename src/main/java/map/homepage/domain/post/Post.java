package map.homepage.domain.post;

import jakarta.persistence.*;
import lombok.*;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.Role;
import map.homepage.domain.post.comment.Comment;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동 생성
    private Long id; // 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn // 외래키
    private Member member; // 게시글 작성자

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String dtype; // 게시글 타입
    private int views; // 게시글 조회 수

    @Enumerated(EnumType.STRING) // Role에서 enum으로 가져옴
    private Role role; // 읽기 권한


    /* 데이터 정합성을 위한 편의 메소드
    public void addComment(Comment comment){
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setPost(null);
    }
    */
}

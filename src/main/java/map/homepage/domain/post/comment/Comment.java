package map.homepage.domain.post.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.common.BaseEntity;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.post.Post;

@Entity // 해당 클래스가 JPA의 엔티티임을 명시
@Builder // 빌더 패턴 (디자인 패턴)
@Getter // getter 생성
@NoArgsConstructor
@AllArgsConstructor // 빌더 패턴 사용을 위해
public class Comment extends BaseEntity {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    private Long id;

    //@Size(min=1, max=100, message = "COMMENT_TOO_LONG") // 댓글 길이 제한
    @Column(columnDefinition = "TEXT")
    @Size(max=300, message = "COMMENT_TOO_LONG")
    private String content;

    // 외래키
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 읽기 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    public Comment(String content, Member member, Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }
}

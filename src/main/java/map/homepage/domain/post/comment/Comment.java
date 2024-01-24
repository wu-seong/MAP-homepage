package map.homepage.domain.post.comment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.common.BaseEntity;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.Role;
import map.homepage.domain.post.Post;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity // 해당 클래스가 JPA의 엔티티임을 명시
@Builder // 빌더 패턴 (디자인 패턴)
@Getter // getter 생성
@NoArgsConstructor
@AllArgsConstructor // 빌더 패턴 사용을 위해
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // 외래키
    @ManyToOne // 지연 로딩
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // 연관된 member가 삭제 되면 같이 삭제
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    // 읽기 권한
    @Enumerated(EnumType.STRING)
    private Role role;

    public Comment(final String content, final Member member, final Post post) {
        this.content = content;
        this.member = member;
        this.post = post;
    }

    public boolean isOwnComment(Member member) {
        return this.member.equals(member);
    }
}

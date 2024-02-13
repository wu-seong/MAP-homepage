// Post.java
package map.homepage.domain.post;

import jakarta.persistence.*;
import lombok.*;
import map.homepage.domain.common.BaseEntity;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.post.comment.Comment;
import map.homepage.domain.post.image.Image;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id // 기본키
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동 생성
    private Long id; // 게시글 ID

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩
    @JoinColumn(name = "member_id") // 외래키
    private Member member; // 게시글 작성자

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Image> images;

    @Enumerated(EnumType.STRING) // Role에서 enum으로 가져옴
    private Role role; // 읽기 권한

    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String dtype; // 게시글 타입
    private int views; // 게시글 조회 수
    private String thumbnail; // 게시글 썸네일

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

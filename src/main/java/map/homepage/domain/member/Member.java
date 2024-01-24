package map.homepage.domain.member;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.common.BaseEntity;
import map.homepage.domain.member.enums.Role;
import map.homepage.domain.member.enums.SocialType;
import map.homepage.domain.member.enums.Status;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.comment.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity // -> JPA가 인식하고 테이블로 변환시킬 객체에 써주면 됨
@Builder // -> builder 패턴 적용
@Getter // -> 각 속성마다 get메서드 안만들어도 됨
@NoArgsConstructor  //모든 JPA 엔티티는 기본 생성자를 가지고 있어야 한다.
@AllArgsConstructor //builder 사용을 위해서 추가, 빌더만 추가하면 오류남
@DynamicInsert // null이 아닌 속성만을 SQL 쿼리에 포함
@DynamicUpdate // 변경 속성만 쿼리에 포함 -> 성능 향상
@SQLDelete(sql = "UPDATE member SET status = 'INACTIVE' WHERE id = ?") //soft delete
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;

    private Long oauthId;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean infoSet; //추가 정보 기입 여부

    private String name;

    private String grade;

    private String email; // oauth2 제공자가 알려준 email

    @Column(columnDefinition = " VARCHAR(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime inactiveDate;

    private String nickname;

    private LocalDate birth;

    private String imageUri; //프로필 이미지 경로

    @Enumerated(EnumType.STRING)
    private SocialType socialType; //oauth2 제공자

    @Enumerated(EnumType.STRING)
    private Role role;



    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> postList;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // 코멘트 완성 되면 주석 해제
    private List<Comment> commentList;
    public void update(String stuId, String nickname, String grade) {
        this.studentId = stuId;
        this.nickname = nickname;
        this.grade = grade;
    }
    public void setMember(){
        this.infoSet = true;
        this.role = Role.USER;
    }

    // 관리자 권한인지 확인하는 메소드
    public boolean isAdmin() {
        return Role.ADMIN.equals(this.role);
    }
}


package map.homepage.domain.member;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.post.Post;
import map.homepage.domain.post.comment.Comment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // -> JPA가 인식하고 테이블로 변환시킬 객체에 써주면 됨
@Builder // -> builder 패턴 적용
@Getter // -> 각 속성마다 get메서드 안만들어도 됨
@NoArgsConstructor  //모든 JPA 엔티티는 기본 생성자를 가지고 있어야 한다.
@AllArgsConstructor //builder 사용을 위해서 추가, 빌더만 추가하면 오류남
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String oauthId;

    private String name;

    private String grade;

    private String email; // oauth2 제공자가 알려준 email

    private String status;

    private LocalDateTime inactiveDate;

    private String nickname;

    private LocalDate birth;

    private String imageUri; //프로필 이미지 경로

    private String provider; //oauth2 제공자

    @Enumerated(EnumType.STRING)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL) 코멘트 완성 되면 주석 해제
//    private List<Comment> commentList = new ArrayList<>();



    public void setOauthId(String oauthId){
        this.oauthId = oauthId;
    }
    public Member update(String name, String email, String imageUri) {
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
        return this;
    }
}


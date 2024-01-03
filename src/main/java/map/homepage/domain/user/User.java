package map.homepage.domain.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor  //모든 JPA 엔티티는 기본 생성자를 가지고 있어야 한다.
@AllArgsConstructor //builder 사용을 위해서 추가
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String oauthId;

    private String name;

    private String email; // oauth2 제공자가 알려준 email

    private String imageUri; //프로필 이미지 경로

    private String provider; //oauth2 제공자

    @Enumerated(EnumType.STRING)
    private Role role;

    public void setOauthId(String oauthId){
        this.oauthId = oauthId;
    }
    public User update(String name, String email, String imageUri) {
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
        return this;
    }
}


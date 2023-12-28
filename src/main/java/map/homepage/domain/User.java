package map.homepage.domain;


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
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String name;

    private String email;

    private String imageUrl;

//    @Enumerated(EnumType.STRING)
//    private Role role;
}


package map.homepage.domain.member;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import map.homepage.domain.post.Post;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProfileImage{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String originalName;
    private String storageName;
    private String imageUrl;
}

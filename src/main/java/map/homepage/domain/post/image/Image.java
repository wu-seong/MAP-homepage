//Image.java
package map.homepage.domain.post.image;

import jakarta.persistence.*;
import lombok.*;
import map.homepage.domain.common.BaseEntity;
import map.homepage.domain.post.Post;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image extends BaseEntity {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalName;
    private String storageName;
    private String imageUrl;
}

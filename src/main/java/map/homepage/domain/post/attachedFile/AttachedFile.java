package map.homepage.domain.post.attachedFile;

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
public class AttachedFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String originalName;
    private String storageName;
    private String fileUrl;
}
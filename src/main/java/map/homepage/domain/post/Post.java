package map.homepage.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post { // 게시글의 정보

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 게시글 ID

    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String dtype; // 게시글 타입

    //private
    //
}

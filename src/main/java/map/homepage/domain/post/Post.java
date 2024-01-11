package map.homepage.domain.post;

import jakarta.persistence.*;
import lombok.*;
import map.homepage.domain.member.Member;
import map.homepage.domain.member.Role;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동 생성
    private Long id; // 게시글 ID

    // 아직 매핑 불충분해서 주석처리
    //@JoinColumn(name = "member_id") // 외래키
    //private Member member;

    // 필수
    private String title; // 게시글 제목
    private String content; // 게시글 내용
    private String dtype; // 게시글 타입

    @Enumerated(EnumType.STRING) //Role에서 enum으로 가져옴
    private Role role; // 읽기 권한

    // 선택
    @Lob
    private byte[] fileData; // 첨부 파일

    // 첨부파일 저장되면 썸네일을 생성하는 로직 필요
    private String tuhmbnail; // 썸네일
}

package map.homepage.domain.member;

import map.homepage.domain.member.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByOauthId(Long OauthId);
    Page<Member> findAllByStatus(Status status, PageRequest pageRequest);
    Page<Member> findAll(PageRequest pageRequest);
}

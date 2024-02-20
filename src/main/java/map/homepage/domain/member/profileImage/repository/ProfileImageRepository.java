package map.homepage.domain.member.profileImage.repository;

import map.homepage.domain.member.Member;
import map.homepage.domain.member.profileImage.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    Optional<ProfileImage> findByMember(Member member);
}

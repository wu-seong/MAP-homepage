package map.homepage.domain.member;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum Role {
    GUEST("ROLE_GUEST"),
    USER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN");

    private String value;
}

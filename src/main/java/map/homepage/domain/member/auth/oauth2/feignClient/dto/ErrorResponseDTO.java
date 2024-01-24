package map.homepage.domain.member.auth.oauth2.feignClient.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDTO {
    private String msg;
    private int code;
}
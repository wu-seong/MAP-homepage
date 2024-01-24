package map.homepage.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import map.homepage.apiPayload.code.BaseCode;
import map.homepage.apiPayload.code.ReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseCode code;

    public ReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}
package map.homepage.exception.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import map.homepage.exception.validation.annotation.CommentValidation;
import map.homepage.apiPayload.code.status.ErrorStatus;

public class CommentValidator implements ConstraintValidator<CommentValidation,String> {

    @Override
    public void initialize(CommentValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.COMMENT_IS_EMPTY.toString()).addConstraintViolation();
        }
        else if (value.isBlank()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.COMMENT_IS_BLANK.toString()).addConstraintViolation();
        }
        else if (value.length() > 100) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.COMMENT_TOO_LONG.toString()).addConstraintViolation();
        }
        return true;
    }
}

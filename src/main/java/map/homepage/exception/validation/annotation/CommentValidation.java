package map.homepage.exception.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import map.homepage.exception.validation.validator.CommentValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CommentValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentValidation {

    String message() default ".";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
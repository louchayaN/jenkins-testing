package com.epam.esm.task5.repository.searchcriteria.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {PositiveOrZeroNumberValidator.class})
public @interface PositiveOrZeroNumber {

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String message() default "The number should be positive or zero decimal number";
}

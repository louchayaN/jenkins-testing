package com.epam.esm.task5.repository.searchcriteria.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MaxNumberValidator.class})
public @interface MaxNumber {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String message() default "Value is out of range";

    long value() default Long.MAX_VALUE;
}

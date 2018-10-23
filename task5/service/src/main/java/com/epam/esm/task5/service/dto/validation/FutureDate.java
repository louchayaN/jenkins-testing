package com.epam.esm.task5.service.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {FutureDateValidator.class})
public @interface FutureDate {

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String message() default "The date should be date in future";
}

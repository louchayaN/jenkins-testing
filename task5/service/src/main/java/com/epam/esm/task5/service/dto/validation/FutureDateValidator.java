package com.epam.esm.task5.service.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class FutureDateValidator implements ConstraintValidator<FutureDate, Long> {

  @Override
  public boolean isValid(Long date, ConstraintValidatorContext context) {
    if (date == null) {
      return false;
    }
    Instant dateTruncatedToDay = Instant.now().truncatedTo(ChronoUnit.DAYS);

    return Instant.ofEpochSecond(date).isAfter(dateTruncatedToDay);
  }
}

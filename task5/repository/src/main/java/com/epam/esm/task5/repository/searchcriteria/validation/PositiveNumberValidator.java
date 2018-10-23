package com.epam.esm.task5.repository.searchcriteria.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PositiveNumberValidator implements ConstraintValidator<PositiveNumber, String> {

  @Override
  public boolean isValid(String numberAsString, ConstraintValidatorContext context) {
    if (numberAsString == null) {
      return true;
    }

    try {
      long number = Long.parseLong(numberAsString);
      return number > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}

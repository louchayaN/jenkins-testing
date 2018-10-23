package com.epam.esm.task5.repository.searchcriteria.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxNumberValidator implements ConstraintValidator<MaxNumber, String> {

  private long maxValue;

  @Override
  public void initialize(MaxNumber constraintAnnotation) {
    this.maxValue = constraintAnnotation.value();
  }

  @Override
  public boolean isValid(String numberAsString, ConstraintValidatorContext context) {
    if (numberAsString == null) {
      return true;
    }

    try {
      long number = Long.parseLong(numberAsString);
      return number <= maxValue;
    } catch (NumberFormatException e) {
      return true;
    }
  }
}

package com.epam.esm.task5.service.util.mapper;

import org.modelmapper.AbstractConverter;

import java.time.Instant;

public class InstantToLongConverter extends AbstractConverter<Instant, Long> {

  @Override
  protected Long convert(Instant instant) {
    if (instant == null) {
      return null;
    }
    return instant.getEpochSecond();
  }
}

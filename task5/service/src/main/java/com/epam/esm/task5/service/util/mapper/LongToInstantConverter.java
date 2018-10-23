package com.epam.esm.task5.service.util.mapper;

import org.modelmapper.AbstractConverter;

import java.time.Instant;

public class LongToInstantConverter extends AbstractConverter<Long, Instant> {

  @Override
  protected Instant convert(Long time) {
    if (time == null) {
      return null;
    }
    return Instant.ofEpochSecond(time);
  }
}

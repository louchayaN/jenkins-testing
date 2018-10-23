package com.epam.esm.task5.service.util.mapper;

import org.modelmapper.AbstractProvider;

import java.time.Instant;

public class InstantProvider extends AbstractProvider<Instant> {

  @Override
  protected Instant get() {
    return Instant.now();
  }
}

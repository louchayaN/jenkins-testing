package com.epam.esm.task5.repository.config;

import com.epam.esm.task5.repository.audit.AuditorAwareImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EntityScan(basePackages = "com.epam.esm.task5.repository")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DataConfig {

  @Bean
  public AuditorAware<Long> auditorAware() {
    return new AuditorAwareImpl();
  }
}

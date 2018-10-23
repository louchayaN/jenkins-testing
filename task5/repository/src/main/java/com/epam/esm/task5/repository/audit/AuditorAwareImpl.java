package com.epam.esm.task5.repository.audit;

import com.epam.esm.task5.repository.util.TokenUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<Long> {

  @Override
  public Long getCurrentAuditor() {
    return TokenUtil.getUserIdFromToken(SecurityContextHolder.getContext().getAuthentication());
  }
}

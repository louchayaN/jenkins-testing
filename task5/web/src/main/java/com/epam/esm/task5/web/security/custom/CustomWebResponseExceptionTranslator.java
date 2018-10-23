package com.epam.esm.task5.web.security.custom;

import com.epam.esm.task5.service.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomWebResponseExceptionTranslator
    implements WebResponseExceptionTranslator<ResponseMessage> {

  private WebResponseExceptionTranslator<OAuth2Exception> defaultTranslator;

  private MessageSource messageSource;

  @Autowired
  public void setDefaultTranslator(WebResponseExceptionTranslator<OAuth2Exception> defaultTranslator) {
    this.defaultTranslator = defaultTranslator;
  }

  @Autowired
  public void setMessages(ResourceBundleMessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public ResponseEntity<ResponseMessage> translate(Exception e) throws Exception {
    ResponseEntity<OAuth2Exception> defaultResponse = defaultTranslator.translate(e);
    HttpStatus statusCode = defaultResponse.getStatusCode();

    Locale locale = LocaleContextHolder.getLocale();

    ResponseMessage errorResponse;
    if (HttpStatus.FORBIDDEN.equals(statusCode)) {
      String message = messageSource.getMessage("oauth2.access.forbidden", new Object[] {}, locale);
      errorResponse = new ResponseMessage(statusCode.value(), message);
    } else if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {
      String message = messageSource.getMessage("oauth2.access.denied", new Object[] {}, locale);
      errorResponse = new ResponseMessage(statusCode.value(), message);
    } else {
      errorResponse = new ResponseMessage(statusCode.value(), defaultResponse.getBody().getMessage());
    }

    return new ResponseEntity<>(errorResponse, statusCode);
  }
}

package com.epam.esm.task5.web.security.custom;

import com.epam.esm.task5.service.dto.user.CustomUserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class CustomTokenEnhancer implements TokenEnhancer {

  private static final String ID_USER_JWT_CLAIM = "sub";

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

    CustomUserDetails user =
        (CustomUserDetails) authentication.getUserAuthentication().getPrincipal();
    Map<String, Object> additionalInfo = new HashMap<>();
    additionalInfo.put(ID_USER_JWT_CLAIM, user.getId());
    ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

    return accessToken;
  }
}

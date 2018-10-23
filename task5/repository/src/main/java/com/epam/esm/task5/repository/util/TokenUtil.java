package com.epam.esm.task5.repository.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.Map;

public class TokenUtil {

    private static final String ID_USER_JWT_CLAIM = "sub";

    private static final String OWNER_ROLE = "ROLE_OWNER";
    private static final String ADMIN_ROLE = "ROLE_ADMIN";

    private TokenUtil() {
        throw new AssertionError("Class contains static methods only");
    }

    public static Long getUserIdFromToken(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        OAuth2AuthenticationDetails oauthDetails =
                (OAuth2AuthenticationDetails) authentication.getDetails();
        Map<String, Object> decodedDetails = (Map<String, Object>) oauthDetails.getDecodedDetails();
        return Long.valueOf(decodedDetails.get(ID_USER_JWT_CLAIM).toString());
    }

    public static boolean isPrincipalHasOwnerRole(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(OWNER_ROLE));
    }

    public static boolean isPrincipalHasAdminRole(Authentication authentication) {
        return authentication
                .getAuthorities()
                .stream()
                .anyMatch(r -> r.getAuthority().equals(ADMIN_ROLE));
    }
}

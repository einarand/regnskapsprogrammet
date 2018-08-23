package org.einar.regnskap.rema1000.auth;

import java.io.Serializable;

public interface AuthorizationProvider extends Serializable {

    String getBearerAccessToken();
}

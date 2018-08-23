package org.einar.regnskap.rema1000.auth;

public interface GetAccessToken {

    boolean isRefreshing();

    RemaAccessToken getAccessToken(TokenStore tokenStore);
}

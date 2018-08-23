package org.einar.regnskap.rema1000.auth;

import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;
import org.einar.regnskap.rema1000.client.RemaAuthClient;

public class GetAccessTokenLogic implements GetAccessToken, AuthorizationProvider {

    private static final Logger logger = Logger.getLogger(GetAccessTokenLogic.class);

    private static GetAccessTokenLogic getAccessToken;
    private final static long DELAY_IN_MS = 250;

    private RemaAuthClient authClient;
    private boolean isRefreshing;
    private Semaphore mutex;

    private GetAccessTokenLogic(RemaAuthClient authClient) {
        this.authClient = authClient;
        this.mutex = new Semaphore(1, true);
    }

    public static GetAccessTokenLogic getInstance(RemaAuthClient authClient) {
        if (getAccessToken == null) {
            getAccessToken = new GetAccessTokenLogic(authClient);
        }
        return getAccessToken;
    }

    public String getBearerAccessToken() {
        TokenStore tokenStore = TokenStore.getInstance();
        return getAccessToken(tokenStore).getAccessTokenBearer();
    }

    public RemaAccessToken getAccessToken(final TokenStore tokenStore) {
        return tokenStore.getAccessToken()
                         .filter(this::isAccessTokenValid)
                         .orElseGet(() -> refreshAccessToken(tokenStore, authClient));
    }

    private void acquireMutex() {
        try {
            this.mutex.acquire();
            logger.info("Mutex acquired");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isAccessTokenValid(RemaAccessToken a) {
        boolean valid = !a.isExpired();
        if (valid) {
            logger.info("Found valid accessToken. Expires: " + a.getExpires());
        } else {
            logger.info("AccessToken expired");
        }
        return valid;
    }

    private RemaAccessToken refreshAccessToken(TokenStore tokenStore, RemaAuthClient authClient) {
        acquireMutex();
        while (isRefreshing()) {
            delay(DELAY_IN_MS);
        }
        this.isRefreshing = true;
        try {
            return tokenStore.getAccessToken().map(oldAccessToken -> {
                String refreshToken = oldAccessToken.getRefreshToken();
                logger.info("Refreshing accessToken");
                return authClient.refreshAccessToken(refreshToken);
            }).orElseThrow(() -> new RuntimeException("Missing refreshToken"));
        } finally {
            this.isRefreshing = false;
            this.mutex.release();
            logger.info("Mutex released");
        }
    }

    public boolean isRefreshing() {
        return isRefreshing;
    }

    private void delay(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

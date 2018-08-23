package org.einar.regnskap.rema1000.auth;

import java.io.Serializable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public final class MediaStoreToken implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String timestamp;
    private final String token;
    private final String urlAuthToken;

    public MediaStoreToken(String token) {
        this.token = checkNotNull(token);
        this.timestamp = extractTimestamp(token);
        this.urlAuthToken = extractAuthToken(token);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getToken() {
        return token;
    }

    public String getUrlAuthToken() {
        return urlAuthToken;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > 1000 * Long.parseLong(timestamp);
    }

    private static String extractTimestamp(String token) {
        return token.split("=")[1].split("~")[0];
    }

    private static String extractAuthToken(String token) {
        return token.split("=")[2].split("/")[3];
    }
}

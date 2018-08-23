package org.einar.regnskap.rema1000.auth;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class RemaAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("access_token")
    private String accessToken;
    private Date expires;
    @SerializedName("expires_in")
    private long expiresIn;
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return this.refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpires() {
        return this.expires;
    }

    public void setExpires(long expiresIn) {
        this.expires = new Date(1000 * ((System.currentTimeMillis() / 1000) + (expiresIn - 60)));
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public long getExpiresIn() {
        return this.expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public boolean isExpired() {
        return getExpires().before(new Date());
    }

    public void setExpiryForSharedLoginUse() {
        setExpiresIn(Long.valueOf(getExpires().getTime() / 1000).longValue());
    }

    public String getAccessTokenBearer() {
        return "Bearer " + this.accessToken;
    }

    public int describeContents() {
        return 0;
    }
}

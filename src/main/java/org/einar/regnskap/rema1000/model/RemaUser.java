package org.einar.regnskap.rema1000.model;

import com.google.gson.annotations.SerializedName;
import org.einar.regnskap.rema1000.auth.RemaAccessToken;

public class RemaUser {
    @SerializedName("mid")
    private String advertisingId;
    private String email;
    @SerializedName("identity")
    private String idpId;
    private boolean isLinkedWithMobilePay = false;
    @SerializedName("sub")
    private String phoneNumber;
    private boolean replaceProductTopList;
    @SerializedName("storeboxid")
    private String storeBoxId;
    private RemaAccessToken token;
    @SerializedName("account_number")
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdpId() {
        return this.idpId;
    }

    public void setIdpId(String idpId) {
        this.idpId = idpId;
    }

    public String getStoreBoxId() {
        return this.storeBoxId;
    }

    public void setStoreBoxId(String storeBoxId) {
        this.storeBoxId = storeBoxId;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RemaAccessToken getToken() {
        return this.token;
    }

    public void setToken(RemaAccessToken token) {
        this.token = token;
    }

    public boolean getIsLinkedWithMobilePay() {
        return this.isLinkedWithMobilePay;
    }

    public boolean shouldReplaceProductTopListAutomatically() {
        return this.replaceProductTopList;
    }

    public void setReplaceProductTopListAutomatically(boolean replaceProductTopList) {
        this.replaceProductTopList = replaceProductTopList;
    }

    public void setIsLinkedWithMobilePay(boolean isLinked) {
        this.isLinkedWithMobilePay = isLinked;
    }

    public boolean hasValidUserData() {
        return this.userId != null && !this.userId.isEmpty();
    }

    public String getAdvertisingId() {
        return this.advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    public int describeContents() {
        return 0;
    }

}

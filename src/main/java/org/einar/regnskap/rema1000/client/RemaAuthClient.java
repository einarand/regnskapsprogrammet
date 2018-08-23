package org.einar.regnskap.rema1000.client;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.einar.regnskap.rema1000.auth.RemaAccessToken;
import org.einar.regnskap.rema1000.model.RemaUser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;
import static org.einar.regnskap.rema1000.RetrofitExecutor.execute;
import static org.einar.regnskap.rema1000.RetrofitExecutor.executeAndReturnBody;
import static org.einar.regnskap.rema1000.client.ServerProtocol.EXTRA_TOKEN;

public class RemaAuthClient {

    private Gson gson = new Gson();
    private static final String REFRESH_TOKEN = "refresh_token";
    private static RemaAuthClient instance;
    private final String baseUrl;
    private IdpAPI api;
    private String authCookie;
    private String idpAppClientToken;

    public static RemaAuthClient getInstance(String baseUrl,
                                             String idpAppClientToken,
                                             GetHeader getHeader) {
        if (instance == null) {
            instance = new RemaAuthClient(baseUrl, getHeader);
            instance.idpAppClientToken = idpAppClientToken;
        }
        return instance;
    }

    private interface IdpAPI {
        @POST("secure/json/authenticate?realm=/app")
        Call<ResponseBody> authCodePost(@Header("Cookie") String str, @Body JsonObject jsonObject);

        @POST("secure/json/authenticate?realm=/app")
        Call<ResponseBody> authPost(@Body String str);

        @POST("secure/json/authenticate?realm=/app")
        Call<ResponseBody> authUserPost(@Header("Cookie") String str, @Body JsonObject jsonObject);

        @FormUrlEncoded
        @POST("secure/oauth2/access_token?realm=/app")
        @Headers({"Cache-Control:no-cache"})
        Call<RemaAccessToken> getAccessToken(@Header("Authorization") String str, @FieldMap Map<String, String> map);

        @FormUrlEncoded
        @POST("secure/oauth2/authorize?realm=/app")
        Call<ResponseBody> oauth2Token(@Header("Cookie") String str, @FieldMap Map<String, String> map);

        @GET("secure/oauth2/userinfo")
        Call<RemaUser> tokenInfoGet(@Header("Authorization") String str);
    }

    private RemaAuthClient(String baseUrl, GetHeader getHeader) {
        this.baseUrl = checkNotNull(baseUrl);
        this.api = IdpRetrofitClient.getInstance(baseUrl, checkNotNull(getHeader))
                                    .create(IdpAPI.class);
    }

    public String getToken() {
        try {
            Response<ResponseBody> response = api.authPost("{}").execute();
            this.authCookie = String.join("; ", response.headers().values("Set-Cookie"));
            ResponseBody body = response.body();
            return gson.fromJson(body.charStream(), JsonObject.class)
                       .get("authId")
                       .getAsString();

        } catch (IOException e) {
            throw new ClientException("Cannot retrieve token to start OTP flow", e);
        }
    }

    public void postPhoneNumber(String phoneNumber, String token) {
        execute(api.authUserPost(authCookie, createPhonePayload(token, "47" + phoneNumber)));
    }

    public String postOtp(String otp, String token) {
        ResponseBody response = execute(api.authCodePost(authCookie, createOTPObject(otp, token)));
        String tokenId = gson.fromJson(response.charStream(), JsonObject.class).get("tokenId").getAsString();
        new JsonObject().addProperty(EXTRA_TOKEN, tokenId);
        return tokenId;
    }

    public String getOauth2Token(String ssoToken) {
        String secureToken = "secureToken=" + ssoToken;
        String redirect = baseUrl + "secure/bella.html";
        String nonce = UUID.randomUUID().toString();
        Map<String, String> fields = new HashMap<>();
        fields.put(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, redirect);
        fields.put("nonce", nonce);
        fields.put("decision", "allow");
        fields.put(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code");
        fields.put("client_id", "app-client");
        fields.put("scope", "account_number identity");
        fields.put("csrf", ssoToken);
        try {
            Response<ResponseBody> response = api.oauth2Token(secureToken, fields).execute();
            URI url = URI.create(response.headers().get("Location"));
            List<NameValuePair> params = URLEncodedUtils.parse(url, "UTF-8");
            String code = params.stream()
                                .filter(p -> p.getName().equals("code"))
                                .map(NameValuePair::getValue)
                                .findAny()
                                .orElseThrow(() -> new RuntimeException("Code not found in " + url.toString()));
            return code;
        } catch (IOException e) {
            throw new ClientException("Could not retrieve Oauth2 token", e);
        }

    }

    public RemaAccessToken getAccessToken(String code) {
        String base = new String(Base64.getEncoder().encode(idpAppClientToken.getBytes()));
        String redirect = baseUrl + "secure/bella.html";
        Map<String, String> fields = new HashMap<>();
        fields.put(ServerProtocol.DIALOG_PARAM_REDIRECT_URI, redirect);
        fields.put(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code");
        fields.put("grant_type", "authorization_code");
        fields.put("code", code);
        return execute(api.getAccessToken("Basic " + base, fields));
    }

    public RemaAccessToken refreshAccessToken(String refreshToken) {
        String base = new String(Base64.getEncoder().encode(idpAppClientToken.getBytes()));
        Map<String, String> fields = new HashMap<>();
        fields.put("grant_type", REFRESH_TOKEN);
        fields.put(ServerProtocol.DIALOG_PARAM_RESPONSE_TYPE, "code");
        fields.put(REFRESH_TOKEN, refreshToken);
        return execute(api.getAccessToken("Basic " + base, fields));
    }

    public RemaUser getUserInfo(String token) throws IOException {
        return execute(api.tokenInfoGet(token));
    }

    private JsonObject createPhonePayload(String authToken, String phoneNumberWithPrefix) {
        JsonObject userObject = new JsonObject();
        userObject.addProperty("stage", "ABPhone2");
        JsonObject inputObject = new JsonObject();
        inputObject.addProperty("name", "IDToken1");
        inputObject.addProperty("value", phoneNumberWithPrefix);
        JsonArray inputArray = new JsonArray();
        inputArray.add(inputObject);
        JsonObject callbackObject = new JsonObject();
        callbackObject.addProperty("type", "NameCallback");
        callbackObject.add("input", inputArray);
        JsonArray callbacks = new JsonArray();
        callbacks.add(callbackObject);
        userObject.add("callbacks", callbacks);
        userObject.addProperty("authId", authToken);
        return userObject;
    }

    private JsonObject createOTPObject(String otp, String token) {
        JsonObject otpObject = new JsonObject();
        otpObject.addProperty("authId", token);
        JsonArray callbacks = new JsonArray();
        JsonObject callbackObject1 = new JsonObject();
        JsonObject inputObject1 = new JsonObject();
        inputObject1.addProperty("name", "IDToken1");
        inputObject1.addProperty("value", otp);
        JsonArray inputArray1 = new JsonArray();
        inputArray1.add(inputObject1);
        callbackObject1.add("input", inputArray1);
        callbackObject1.addProperty("type", "PasswordCallback");
        callbacks.add(callbackObject1);
        JsonObject callbackObject2 = new JsonObject();
        JsonObject inputObject2 = new JsonObject();
        inputObject2.addProperty("name", "IDToken2");
        inputObject2.addProperty("value", 0);
        JsonArray inputArray2 = new JsonArray();
        inputArray2.add(inputObject2);
        callbackObject2.add("input", inputArray2);
        callbackObject2.addProperty("type", "ConfirmationCallback");
        callbacks.add(callbackObject2);
        otpObject.add("callbacks", callbacks);
        otpObject.addProperty("stage", "HOTP2");
        return otpObject;
    }
}

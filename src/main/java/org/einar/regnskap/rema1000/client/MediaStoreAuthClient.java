package org.einar.regnskap.rema1000.client;

import java.io.IOException;

import okhttp3.ResponseBody;
import org.einar.regnskap.rema1000.BuildConfig;
import org.einar.regnskap.rema1000.auth.GetAccessTokenLogic;
import org.einar.regnskap.rema1000.auth.MediaStoreToken;
import retrofit2.Call;
import retrofit2.http.GET;

import static org.einar.regnskap.rema1000.RetrofitExecutor.execute;

public class MediaStoreAuthClient {
    private static MediaStoreAuthClient instance;
    private TokenAPI tokenAPI = SharedRetrofitHelper.getInstance(BuildConfig.BASE_URL,
                                                                 BuildConfig.API_SUBSCRIPTION_KEY,
                                                                 GetAccessTokenLogic.getInstance(
                                                                     RemaAuthClient.getInstance(
                                                                         BuildConfig.BASE_IDP_URL,
                                                                         BuildConfig.IDP_APP_CLIENT_TOKEN,
                                                                         GetHeaderLogic.getInstance()
                                                                     )
                                                                 ),
                                                                 GetHeaderLogic.getInstance(),
                                                                 new ApiHeader[0])
                                                    .create(TokenAPI.class);

    public interface TokenAPI {
        @GET("bellams/gettoken")
        Call<ResponseBody> getToken();
    }

    private MediaStoreAuthClient() {
    }

    public static MediaStoreAuthClient getInstance() {
        if (instance == null) {
            instance = new MediaStoreAuthClient();
        }
        return instance;
    }

    public MediaStoreToken getToken() {
        return new MediaStoreToken(asString(execute(tokenAPI.getToken())));
    }

    private static String asString(ResponseBody responseBody) {
        try {
            return responseBody.string();
        } catch (IOException e) {
            throw new ClientException("IOException while getting string from body", e);
        }
    }
}

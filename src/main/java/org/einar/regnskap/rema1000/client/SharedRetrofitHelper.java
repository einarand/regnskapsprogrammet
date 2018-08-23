package org.einar.regnskap.rema1000.client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.einar.regnskap.rema1000.auth.AuthorizationProvider;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SharedRetrofitHelper {
    public static Retrofit getInstance(String baseURL, String apiSubscriptionkey,
                                       AuthorizationProvider authorizationProvider,
                                       GetHeader getHeader, ApiHeader... apiHeaders) {
        return createRetrofit(baseURL, apiSubscriptionkey, 15, authorizationProvider, getHeader, apiHeaders);
    }

    private static Retrofit createRetrofit(String baseURL, final String apiSubscriptionKey,
                                           int timeout,
                                           final AuthorizationProvider authorizationProvider,
                                           final GetHeader getHeader,
                                           final ApiHeader... apiHeaders) {
        Builder builder = new Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        builder.addInterceptor(chain -> {
            String str;
            String accessToken = "";
            if (authorizationProvider != null) {
                accessToken = authorizationProvider.getBearerAccessToken();
            }
            Request.Builder requestBuilder = chain.request().newBuilder();
            Request.Builder addHeader = requestBuilder.addHeader("Ocp-Apim-Subscription-Key", apiSubscriptionKey)
                                                      .addHeader("Authorization", accessToken)
                                                      .addHeader("Accept", "application/json")
                                                      .addHeader("Content-Type", "application/json");
            String str2 = "X-Correlation-ID";
            if (getHeader == null) {
                str = "";
            } else {
                str = getHeader.getXCorrelationId();
            }
            addHeader = addHeader.addHeader(str2, str);
            str2 = "X-Device-ID";
            if (getHeader == null) {
                str = "";
            } else {
                str = getHeader.getXDeviceId();
            }
            addHeader = addHeader.addHeader(str2, str);
            str2 = "X-Mobile-NR";
            if (getHeader == null) {
                str = "";
            } else {
                str = getHeader.getUserMobileNumber();
            }
            addHeader = addHeader.addHeader(str2, str);
            str2 = "x-app";
            if (getHeader == null) {
                str = "";
            } else {
                str = getHeader.getAppName();
            }
            addHeader = addHeader.addHeader(str2, str);
            str2 = "x-app-version";
            if (getHeader == null) {
                str = "";
            } else {
                str = getHeader.getAppVersion();
            }
            addHeader.addHeader(str2, str)
                     .addHeader("x-platform", "android");
            if (apiHeaders != null) {
                for (ApiHeader apiHeader : apiHeaders) {
                    requestBuilder.addHeader(apiHeader.getKey(), apiHeader.getValue());
                }
            }
            Enter(requestBuilder);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        return new Retrofit.Builder()
            .baseUrl(baseURL)
            .client(builder.readTimeout((long) timeout, TimeUnit.SECONDS)
                           .connectTimeout((long) timeout, TimeUnit.SECONDS)
                           .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    public static Object Enter(Object builderObject) {
        try {
            okhttp3.Request.Builder builder = (okhttp3.Request.Builder) builderObject;
            for (Map.Entry<String, List<String>> header : ServerCorrelationHeaders.generate().entrySet()) {
                for (String value : header.getValue()) {
                    builder.header(header.getKey(), value);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}

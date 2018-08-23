package org.einar.regnskap.rema1000.client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.log4j.Logger;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

public class IdpRetrofitClient {
    private static Logger logger = Logger.getLogger(IdpRetrofitClient.class);

    public static Retrofit getInstance(String baseUrl, GetHeader getHeader) {
        return createRetrofit(baseUrl, getHeader);
    }

    private static Retrofit createRetrofit(String baseUrl, final GetHeader getHeader) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(System.out::println);
        logging.setLevel(Level.BODY);

        Builder builder = new Builder();
        builder.addInterceptor(logging);
        builder.addInterceptor(chain -> {
            String str;
            Request.Builder addHeader = chain.request()
                                             .newBuilder()
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
            Request.Builder addHeader2 = addHeader.addHeader(str2, str)
                                                  .addHeader("x-platform", "android");
            Enter(addHeader2);
            Request request = addHeader2.build();
            return chain.proceed(request);
        });
        return new Retrofit.Builder().baseUrl(baseUrl)
                                     .client(builder.readTimeout(15, TimeUnit.SECONDS)
                                                    .connectTimeout(15, TimeUnit.SECONDS)
                                                    .followRedirects(false)
                                                    .followSslRedirects(false)
                                                    .build())
                                     .addConverterFactory(GsonConverterFactory.create())
                                     .build();
    }


    private static void Enter(Object builderObject) {
        try {
            okhttp3.Request.Builder builder = (okhttp3.Request.Builder) builderObject;
            for (Map.Entry<String, List<String>> header : ServerCorrelationHeaders.generate().entrySet()) {
                for (String value : header.getValue()) {
                    builder.header(header.getKey(), value);
                }
            }
        } catch (Throwable e) {
            logger.error("Something went wrong", e);
        }
    }


}

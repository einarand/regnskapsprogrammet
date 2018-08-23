package org.einar.regnskap.rema1000.client;

import java.util.List;


import org.einar.regnskap.rema1000.BuildConfig;
import org.einar.regnskap.rema1000.api.ServiceStatus;
import org.einar.regnskap.rema1000.api.TransactionHeads;
import org.einar.regnskap.rema1000.api.TransactionRow;
import org.einar.regnskap.rema1000.auth.GetAccessTokenLogic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static org.einar.regnskap.rema1000.BuildConfig.IDP_APP_CLIENT_TOKEN;
import static org.einar.regnskap.rema1000.RetrofitExecutor.execute;

public class RemaClient {

    private static RemaClient instance;
    private RestAPI restAPI;

    private interface RestAPI {

        @GET("bella/version")
        Call<ServiceStatus> getAppServiceStatus();

        @GET("bella/heads")
        Call<TransactionHeads> getTransactionHeads();

        @GET("bella/rows/{tid}")
        Call<List<TransactionRow>> getTransactionRows(@Path("tid") long j);

    }

    private RemaClient(String baseURL) {
        Retrofit retrofit = SharedRetrofitHelper.getInstance(
            baseURL,
            BuildConfig.API_SUBSCRIPTION_KEY,
            GetAccessTokenLogic.getInstance(
                RemaAuthClient.getInstance(BuildConfig.BASE_IDP_URL, IDP_APP_CLIENT_TOKEN, GetHeaderLogic.getInstance())
            ),
            GetHeaderLogic.getInstance()
        );
        this.restAPI = retrofit.create(RestAPI.class);
    }

    public static RemaClient getInstance(String baseURL) {
        if (instance == null) {
            instance = new RemaClient(baseURL);
        }
        return instance;
    }

    public ServiceStatus getAppServiceStatus() {
        return execute(restAPI.getAppServiceStatus());
    }

    public List<TransactionRow> getTransactionRows(long transactionId) {
        return execute(restAPI.getTransactionRows(transactionId));
    }

    public TransactionHeads getTransactionHeads() {
        return execute(restAPI.getTransactionHeads());
    }

}

package org.einar.regnskap.rema1000;

import java.io.IOException;

import okhttp3.ResponseBody;
import org.einar.regnskap.rema1000.client.ClientException;
import retrofit2.Call;
import retrofit2.Response;

public final class RetrofitExecutor {

    public static <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            throw new ClientException(response.code(), response.message());
        } catch (IOException e) {
            throw new ClientException("Cannot execute call: " + call.request().toString(), e);
        }
    }

    public static String executeAndReturnBody(Call<ResponseBody> call) {
        try {
            return execute(call).string();
        } catch (IOException e) {
            throw new ClientException("Cannot execute call: " + call.request().toString(), e);
        }
    }

}

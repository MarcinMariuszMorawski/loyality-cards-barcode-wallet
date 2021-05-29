package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.CreateAccount;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository.CreateAccountInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public final class CreateAccountService implements ApiConfiguration {
    private CreateAccountInterface createAccountRepository;

    public CreateAccountService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        createAccountRepository = retrofit.create(CreateAccountInterface.class);
    }

    public void createAccount(CreateAccount createAccount) throws IOException {
        Call<ResponseBody> retrofitCall = createAccountRepository.create(createAccount);

        Response<ResponseBody> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }
    }
}
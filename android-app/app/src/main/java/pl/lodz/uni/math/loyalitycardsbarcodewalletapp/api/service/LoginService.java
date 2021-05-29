package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service;

import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Login;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Token;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository.LoginInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public final class LoginService implements ApiConfiguration {
    private LoginInterface loginRepository;
    private String token;
    private String email;

    public LoginService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loginRepository = retrofit.create(LoginInterface.class);
    }

    public void login(Login login) throws IOException {
        Call<Token> retrofitCall = loginRepository.login(login);

        Response<Token> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        if (response.body() == null) {
            throw new IOException("Token generate error");
        }
        token = "Bearer " + response.body().getToken();
        email = login.getEmail();
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }
}
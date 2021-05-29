package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.ChangePassword;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository.UserInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public final class UserService implements ApiConfiguration {
    private UserInterface userRepository;
    private String token;

    public UserService(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.userRepository = retrofit.create(UserInterface.class);
        this.token = token;
    }

    public void changePassword(ChangePassword changePassword) throws IOException {
        Call<ResponseBody> retrofitCall = userRepository.changePassword(token, changePassword);

        Response<ResponseBody> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }
    }
}
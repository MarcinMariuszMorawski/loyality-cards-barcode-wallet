package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository;

import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Login;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Token;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginInterface {
    @POST("user/login")
    Call<Token> login(@Body Login login);
}
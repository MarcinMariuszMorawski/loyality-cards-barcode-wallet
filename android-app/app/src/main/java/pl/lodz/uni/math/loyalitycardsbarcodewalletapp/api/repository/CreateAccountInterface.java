package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.CreateAccount;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CreateAccountInterface {
    @POST("user/createaccount")
    Call<ResponseBody> create(@Body CreateAccount createAccount);
}

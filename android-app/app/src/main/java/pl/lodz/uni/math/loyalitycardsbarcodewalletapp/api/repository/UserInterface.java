package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.ChangePassword;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface UserInterface {
    @PUT("user/changepassword")
    Call<ResponseBody> changePassword(@Header("authorization") String accessToken, @Body ChangePassword changePassword);
}
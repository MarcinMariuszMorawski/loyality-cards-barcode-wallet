package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface CardInterface {
    @GET("card/all")
    Call<List<Card>> getAll(@Header("authorization") String accessToken);

    @POST("card")
    Call<Card> add(@Header("authorization") String accessToken, @Body Card card, @Query("brandId") Long brandId);

    @PUT("card")
    Call<Card> update(@Header("authorization") String accessToken, @Body Card card, @Query("brandId") Long brandId);

    @DELETE("card")
    Call<ResponseBody> delete(@Header("authorization") String accessToken, @Query("id") Long id);
}
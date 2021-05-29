package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository;

import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface BrandInterface {
    @GET("brand/all")
    Call<List<Brand>> listRepos(@Header("authorization") String accessToken);
}
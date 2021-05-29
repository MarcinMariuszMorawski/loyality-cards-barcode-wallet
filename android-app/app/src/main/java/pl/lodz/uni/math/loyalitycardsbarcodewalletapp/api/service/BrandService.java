package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service;

import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository.BrandInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public final class BrandService implements ApiConfiguration {
    private BrandInterface brandRepository;
    private String token;

    public BrandService(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.brandRepository = retrofit.create(BrandInterface.class);
        this.token = token;
    }


    public List<Brand> getAll() throws IOException {
        Call<List<Brand>> retrofitCall = brandRepository.listRepos(token);

        Response<List<Brand>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }
}
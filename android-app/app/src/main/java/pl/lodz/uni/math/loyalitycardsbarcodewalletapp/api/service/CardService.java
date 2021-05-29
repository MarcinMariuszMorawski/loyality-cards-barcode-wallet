package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service;

import okhttp3.ResponseBody;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.repository.CardInterface;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

public final class CardService implements ApiConfiguration {
    private CardInterface cardRepository;
    private String token;

    public CardService(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.cardRepository = retrofit.create(CardInterface.class);
        this.token = token;
    }


    public List<Card> getAll() throws IOException {
        Call<List<Card>> retrofitCall = cardRepository.getAll(token);

        Response<List<Card>> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public Card add(Card card) throws IOException {
        Call<Card> retrofitCall = cardRepository.add(token, card, card.getBrand().getId());

        Response<Card> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public Card update(Card card) throws IOException {
        Call<Card> retrofitCall = cardRepository.update(token, card, card.getBrand().getId());

        Response<Card> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }

        return response.body();
    }

    public void delete(Card card) throws IOException {
        Call<ResponseBody> retrofitCall = cardRepository.delete(token, card.getId());

        Response<ResponseBody> response = retrofitCall.execute();

        if (!response.isSuccessful()) {
            throw new IOException(response.errorBody() != null
                    ? response.errorBody().string() : "Unknown error");
        }
    }
}
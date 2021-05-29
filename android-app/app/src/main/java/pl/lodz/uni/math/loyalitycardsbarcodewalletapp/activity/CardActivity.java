package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.CardService;

import java.io.IOException;

public final class CardActivity extends AppCompatActivity {
    private static final String SHARED_PREFERENCES_CARD = "CARD_PREFS";

    private TextView textViewBrandName;
    private ImageView imageViewBarcode;
    private TextView textViewBarcode;
    private ImageButton imageButtonDelete;

    private String token;
    private Card card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        loadToken();
        loadCard();
        initialize();
        drawBarcode();
    }

    private void loadToken() {
        SharedPreferences prefs = getSharedPreferences(ApiConfiguration.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("token", null);
        if (restoredText != null) {
            token = prefs.getString("token", "");
        }
    }

    private void initialize() {
        textViewBrandName = findViewById(R.id.textViewBrandName);
        imageViewBarcode = findViewById(R.id.imageViewBarcode);
        textViewBarcode = findViewById(R.id.textViewBarcode);
        imageButtonDelete = findViewById(R.id.imageButtonDelete);

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCard();
            }
        });
    }

    private void loadCard() {
        Intent intent = getIntent();

        if (intent.hasExtra("Card")) {
            this.card = (Card) intent.getSerializableExtra("Card");
            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES_CARD, MODE_PRIVATE).edit();

            editor.putLong("cardId", card.getId());
            editor.putString("barcode", card.getBarcode());
            editor.putString("format", card.getFormat());
            editor.putString("color", card.getBrand().getColor());
            editor.putLong("brandId", card.getBrand().getId());
            editor.putString("name", card.getBrand().getName());
            editor.apply();
        } else {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_CARD, MODE_PRIVATE);
            String restoredText = prefs.getString("barcode", null);

            if (restoredText != null) {
                Brand brand = new Brand(prefs.getString("name", ""), prefs.getString("color", ""));
                brand.setId(prefs.getLong("brandId", 1L));

                card = new Card(prefs.getString("barcode", ""), prefs.getString("format", ""), brand);
                card.setId(prefs.getLong("cardId", 1L));
            }
        }
    }

    private void drawBarcode() {
        String barcode = card.getBarcode();
        BarcodeFormat barcodeFormat = BarcodeFormat.valueOf(card.getFormat());
        textViewBarcode.setText(barcode);
        textViewBrandName.setText(card.getBrand().getName());

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(barcode, barcodeFormat, 2000, 500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageViewBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void deleteCard() {

        final CardService cardService = new CardService(token);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cardService.delete(card);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backToWallet();
                        }
                    });
                } catch (IOException e) {
                    try {
                        JSONObject json = new JSONObject(e.getMessage());
                        showToast(json.getString("message"));
                    } catch (JSONException jsonException) {
                        showToast(e.getMessage());
                    }
                }
            }
        }).start();
    }

    private void backToWallet() {
        Intent intent = new Intent(CardActivity.this, WalletActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CardActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.CardService;

import java.io.IOException;

public final class AddCardActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_BRAND = "BRAND_PREFS";

    private String token;

    private Brand brand;
    private Card card;

    private TextView textViewBrandName;
    private EditText editTextBarcode;
    private ImageButton imageButtonCamera;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        loadToken();
        loadBrand();
        initialize();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard();
            }
        });

        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCardCodeFromCamera();
            }
        });
        loadCardCodeFromCamera();
    }

    private void loadToken() {
        SharedPreferences prefs = getSharedPreferences(ApiConfiguration.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("token", null);
        if (restoredText != null) {
            token = prefs.getString("token", "");
        }
    }

    private void loadBrand() {
        Intent intent = getIntent();

        if (intent.hasExtra("Brand")) {
            this.brand = (Brand)intent.getSerializableExtra("Brand");
            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES_BRAND, MODE_PRIVATE).edit();

            editor.putLong("id", brand.getId());
            editor.putString("name", brand.getName());
            editor.putString("color", brand.getColor());

            editor.apply();
        } else {
            SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_BRAND, MODE_PRIVATE);
            String restoredText = prefs.getString("name", null);

            if (restoredText != null) {
                brand = new Brand(prefs.getString("name",""), prefs.getString("color",""));
                brand.setId(prefs.getLong("id",0L));
            }
        }
    }
    private void initialize() {
        textViewBrandName = findViewById(R.id.textViewBrandName);
        editTextBarcode = findViewById(R.id.editTextBarcode);
        imageButtonCamera = findViewById(R.id.imageButtonCamera);
        buttonSave = findViewById(R.id.buttonSave);

        textViewBrandName.setText(brand.getName());
        card = new Card();
        card.setBrand(brand);
    }
    private void loadCardCodeFromCamera() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(brand.getName());
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                editTextBarcode.setText(result.getContents());
                card.setBarcode(result.getContents());
                card.setFormat(result.getFormatName());
            }
        }
    }

    private void saveCard() {
        if (card.getBarcode() == null || card.getFormat() == null || card.getBarcode().isEmpty() || card.getFormat().isEmpty()) {
            showToast("Barcode can not be empty");
            return;
        }

        final CardService cardService = new CardService(token);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cardService.add(card);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            backToWallet();
                        }
                    });
                } catch (IOException e) {
                    showToast(e.getMessage());
                }
            }
        }).start();

        backToWallet();
    }

    private void backToWallet() {
        Intent intent = new Intent(AddCardActivity.this, WalletActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddCardActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
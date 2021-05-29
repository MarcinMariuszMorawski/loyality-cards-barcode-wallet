package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity.util.BrandAdapter;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Brand;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.BrandService;

import java.io.IOException;
import java.util.List;

public final class BrandsActivity extends AppCompatActivity {
    private String token;
    private ListView listViewBrands;
    private List<Brand> brands;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands);

        loadToken();
        initialize();
        loadBrands();
    }

    private void loadToken() {
        SharedPreferences prefs = getSharedPreferences(ApiConfiguration.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("token", null);
        if (restoredText != null) {
            token = prefs.getString("token", "");
        }
    }

    private void initialize() {
        listViewBrands = findViewById(R.id.listViewBrands);
    }

    private void loadBrands() {

        final BrandService brandService = new BrandService(token);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    brands = brandService.getAll();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showBrands();
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

    private void showBrands() {
        final BrandAdapter brandAdapter = new BrandAdapter(this, brands);
        listViewBrands.setAdapter(brandAdapter);

        listViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BrandsActivity.this, AddCardActivity.class);
                Brand brand = brandAdapter.getItem(position);
                intent.putExtra("Brand", brand);
                startActivity(intent);
            }
        });
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BrandsActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
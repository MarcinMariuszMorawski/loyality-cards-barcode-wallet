package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity.util.ActivityInformation;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity.util.CardButton;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.CardService;

import java.io.IOException;
import java.util.List;

public final class WalletActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String token;
    private String email;
    private List<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        loadToken();
        initialize();
        loadCards();
    }

    private void loadToken() {
        SharedPreferences prefs = getSharedPreferences(MainActivity.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("token", null);
        if (restoredText != null) {
            token = prefs.getString("token", "");
            email = prefs.getString("email", "");
        }
    }

    private void initialize() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WalletActivity.this,BrandsActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
        textViewEmail.setText(email);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_change_pass) {
            Intent intent = new Intent(WalletActivity.this, ChangePasswordActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(MainActivity.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE).edit();
        editor.putString("token", "");
        editor.putString("email", "");
        editor.apply();

        Intent intent = new Intent(WalletActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    private void loadCards() {
        final CardService cardService = new CardService(token);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    cards = cardService.getAll();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addCardsToScreen();
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

    private void addCardsToScreen() {
        final LinearLayout buttonsLayout = findViewById(R.id.buttonsLayout);

        for (int i = 0; i < cards.size(); i++) {
            LinearLayout newLine = new LinearLayout(this);
            newLine.setOrientation(LinearLayout.HORIZONTAL);
            for (int j = 0; j < 2; j++) {
                int index = i * 2 + j;
                if (index < cards.size()) {
                    CardButton button = createButton(index);
                    newLine.addView(button);
                }
            }
            buttonsLayout.addView(newLine);
        }
    }

    private CardButton createButton(int index) {

        final int halfOfWidth = ActivityInformation.getActivityWidth(WalletActivity.this) / 2;
        CardButton button = new CardButton(this, cards.get(index), halfOfWidth, (int) (halfOfWidth * 0.65));
        button.setOnClickListener(onCardButtonListener);
        return button;
    }

    private final View.OnClickListener onCardButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            CardButton clickedButtonCard = (CardButton) v;
            Card card = clickedButtonCard.getCard();
            Intent intent = new Intent(WalletActivity.this, CardActivity.class);
            intent.putExtra("Card", card);
            startActivity(intent);
        }
    };

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WalletActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
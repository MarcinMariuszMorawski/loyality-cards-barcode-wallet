package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.ApiConfiguration;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.ChangePassword;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.UserService;

import java.io.IOException;

public final class ChangePasswordActivity extends AppCompatActivity {
    private String token;


    private EditText editTextPassword;
    private EditText editTextNewPassword;
    private EditText editTextNewPasswordAgain;
    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loadToken();
        initialize();
    }

    private void loadToken() {
        SharedPreferences prefs = getSharedPreferences(ApiConfiguration.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("token", null);
        if (restoredText != null) {
            token = prefs.getString("token", "");
        }
    }

    private void initialize() {
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewPasswordAgain = findViewById(R.id.editTextNewPasswordAgain);
        buttonCreateAccount = findViewById(R.id.buttonChangePassword);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        if (editTextNewPassword.getText().toString().length() < 6 || editTextNewPassword.getText().toString().length() > 32) {
            showToast("Password must be between 6 and 32 characters");
            return;
        }
        if (!editTextNewPassword.getText().toString().equals(editTextNewPasswordAgain.getText().toString())) {
            showToast("Passwords do not match");
            return;
        }

        final ChangePassword changePassword = new ChangePassword(editTextPassword.getText().toString(), editTextNewPassword.getText().toString());
        changePassword.hashPassword();

        final UserService userService = new UserService(token);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userService.changePassword(changePassword);
                    showToast("Password has been changed, please login again");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            logout();
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

    private void logout() {
        SharedPreferences.Editor editor = getSharedPreferences(ApiConfiguration.SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE).edit();
        editor.putString("token", "");
        editor.putString("email", "");
        editor.apply();

        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChangePasswordActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.CreateAccount;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.CreateAccountService;

import java.io.IOException;

public final class CreateAccountActivity extends AppCompatActivity {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordAgain;

    private Button buttonCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        initialize();
    }

    private void initialize() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextNewPassword);
        editTextPasswordAgain = findViewById(R.id.editTextNewPasswordAgain);
        buttonCreateAccount = findViewById(R.id.buttonChangePassword);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate() {
        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches()) {
            showToast("Wrong email address format");
            return;
        }
        if (editTextPassword.getText().toString().length() < 6 || editTextPassword.getText().toString().length() > 32) {
            showToast("Password must be between 6 and 32 characters");
            return;
        }
        if (!editTextPassword.getText().toString().equals(editTextPasswordAgain.getText().toString())) {
            showToast("Passwords do not match");
            return;
        }

        createAccount();
    }


    private void createAccount() {
        final CreateAccount createAccount = new CreateAccount(editTextEmail.getText().toString(), editTextPassword.getText().toString());
        createAccount.hashPassword();

        final CreateAccountService createAccountService = new CreateAccountService();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createAccountService.createAccount(createAccount);
                    showToast("Account " + createAccount.getEmail() + " has been created");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextEmail.setText("");
                            editTextPassword.setText("");
                            editTextPasswordAgain.setText("");
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

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateAccountActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
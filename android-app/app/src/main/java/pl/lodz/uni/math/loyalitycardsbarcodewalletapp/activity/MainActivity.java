package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.R;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model.Login;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.service.LoginService;

import java.io.IOException;

public final class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES_LOGGED_USER = "WALLET_LOGGED_USER";

    private Button buttonLogin;
    private Button buttonCreateAccount;
    private EditText editTextEmail;
    private EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        checkIfLogged();
    }

    private void initialize() {
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonChangePassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextNewPassword);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login login = new Login(editTextEmail.getText().toString(), editTextPassword.getText().toString());
                login.hashPassword();
                loginAsyncTask(login);
            }
        });

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkIfLogged() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE);
        String restoredText = prefs.getString("email", null);
        if (restoredText != null) {
            String email = prefs.getString("email", "");
            String password = prefs.getString("password", "");

            if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
                loginAsyncTask(new Login(email, password));
            }
        }
    }

    class LoginAsyncTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;
        LoginService loginService;
        Login login;

        public LoginAsyncTask(Login login) {
            this.login = login;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "Login to wallet", true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            loginService = new LoginService();
            try {

                loginService.login(login);
                afterSuccessfulLogin();
                return null;
            } catch (IOException e) {
                try {
                    JSONObject json = new JSONObject(e.getMessage());
                    showToast(json.getString("message"));
                } catch (JSONException jsonException) {
                    showToast(e.getMessage());
                }
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
        }

        private void afterSuccessfulLogin() {
            Intent intent = new Intent(MainActivity.this, WalletActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES_LOGGED_USER, MODE_PRIVATE).edit();
            editor.putString("token", loginService.getToken());
            editor.putString("email", loginService.getEmail());
            editor.putString("password", login.getPassword());
            editor.apply();
            startActivity(intent);
        }
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loginAsyncTask(Login login) {
        LoginAsyncTask task = new LoginAsyncTask(login);
        task.execute();
    }
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public final class CreateAccount {
    private String email;
    private String password;

    public CreateAccount() {
    }

    public CreateAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void hashPassword() {
        password = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}
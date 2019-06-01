package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service;

import org.springframework.stereotype.Component;

@Component
public final class JwtUser {
    private String login;
    private String password;

    public JwtUser() {
    }

    public JwtUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapp.api.model;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public final class ChangePassword {
    private String oldPassword;
    private String newPassword;

    public ChangePassword() {
    }

    public ChangePassword(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void hashPassword() {
        oldPassword = Hashing.sha256()
                .hashString(oldPassword, StandardCharsets.UTF_8)
                .toString();
        newPassword = Hashing.sha256()
                .hashString(newPassword, StandardCharsets.UTF_8)
                .toString();
    }
}
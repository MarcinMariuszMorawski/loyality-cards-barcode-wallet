package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.UserManager;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUserChangePassword;

@RestController
@RequestMapping("/api/user")
public final class UserApi {
    private UserManager userManager;

    @Autowired
    public UserApi(UserManager userManager) {
        this.userManager = userManager;
    }

    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody JwtUserChangePassword jwtUserChangePassword, @RequestHeader HttpHeaders headers) {
        return Responses.wrongRequest();
    }
}
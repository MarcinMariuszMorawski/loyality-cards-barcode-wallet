package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api.exceptions.BadRequestException;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.UserManager;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtAuthenticationServer;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUser;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUserChangePassword;

import java.util.HashMap;
import java.util.LinkedHashMap;

@RestController
@RequestMapping("/api/user")
public final class UserApi {
    private UserManager userManager;
    private JwtAuthenticationServer jwtAuthenticationServer;

    @Autowired
    public UserApi(UserManager userManager, JwtAuthenticationServer jwtAuthenticationServer) {
        this.userManager = userManager;
        this.jwtAuthenticationServer = jwtAuthenticationServer;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtUser jwtUser) {

        String token = jwtAuthenticationServer.validateUserCredentials(jwtUser);

        if (token == null) {
            throw new BadRequestException("Wrong credentials");
        }

        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Token has been generated");
        json.put("token", token);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PostMapping("/createaccount")
    public ResponseEntity<?> createAccount(@RequestBody JwtUser jwtUser) {
        userManager.createAccount(jwtUser);

        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Account has been created");
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @PutMapping("/changepassword")
    public ResponseEntity<?> changePassword(@RequestBody JwtUserChangePassword jwtUserChangePassword, @RequestHeader HttpHeaders headers) {
        userManager.changePassword(jwtUserChangePassword, headers);

        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Password changed");
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}
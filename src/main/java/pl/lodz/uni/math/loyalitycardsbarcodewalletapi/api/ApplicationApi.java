package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtAuthenticationServer;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUser;

@RestController
@RequestMapping("/api/application")
public final class ApplicationApi {
    private JwtAuthenticationServer jwtAuthenticationServer;

    @Autowired
    public ApplicationApi(JwtAuthenticationServer jwtAuthenticationServer) {
        this.jwtAuthenticationServer = jwtAuthenticationServer;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtUser jwtUser) {

        String token = jwtAuthenticationServer.validateUserCredentials(jwtUser);

        if (token == null) {
            return Responses.wrongCredentials();
        }
        return Responses.responseToken(token);
    }
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;

final class Responses {
    static ResponseEntity<?> wrongCredentials() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Niepoprawny login bądź hasło");
        return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
    }

    static ResponseEntity<?> responseToken(String token) {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Wygenerowano token");
        json.put("token", token);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    static ResponseEntity<?> wrongNewPassword() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Niepoprawne nowe hasło");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> wrongRequest() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Niepoprawne zapytanie");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> wrongOldPassword() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Niepoprawne stare hasło");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> noLoginFound() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Brak takiego użytkownika");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> notFound() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Brak takiego rekordu");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> notReturned() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Urzadzenie nie może być zutlizowane kiedy nie zostalo zwrócone");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    static ResponseEntity<?> passwordChanged() {
        HashMap<String, Object> json = new LinkedHashMap<>();
        json.put("message", "Hasło zostało zmienione");
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    static ResponseEntity<?> returnObject(Object object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }
}
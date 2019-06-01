package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.CardManager;

@RestController
@RequestMapping("/api/card")
public final class CardApi {
    private CardManager cardManager;

    @Autowired
    public CardApi(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    @GetMapping("/all")
    public Iterable<Card> getAllUserCards(@RequestHeader HttpHeaders headers) {
        return cardManager.findAllUserCards(headers);
    }

    @PostMapping
    public Card addUserCard(@RequestParam Long brandId, @RequestBody Card card, @RequestHeader HttpHeaders headers) {
        return cardManager.saveUserCard(card, brandId, headers);
    }

    @PutMapping
    public Card updateUserCard(@RequestParam Long brandId, @RequestBody Card card, @RequestHeader HttpHeaders headers) {
        return cardManager.saveUserCard(card, brandId, headers);
    }

    @DeleteMapping
    public void deleteUserCard(@RequestParam Long id, @RequestHeader HttpHeaders headers) {
        cardManager.deleteUserCardById(id, headers);
    }
}
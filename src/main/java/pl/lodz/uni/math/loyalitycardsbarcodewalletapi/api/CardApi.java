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
    public Iterable<Card> getAll(@RequestHeader HttpHeaders headers) {
        return cardManager.findAll();
    }

    @PostMapping
    public Card add(@RequestParam(required = false) Card card, @RequestHeader HttpHeaders headers) {
        return cardManager.save(card);
    }

    @PutMapping
    public Card update(@RequestParam Card card, @RequestHeader HttpHeaders headers) {
        return cardManager.save(card);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id, @RequestHeader HttpHeaders headers) {
        cardManager.deleteById(id);
    }
}
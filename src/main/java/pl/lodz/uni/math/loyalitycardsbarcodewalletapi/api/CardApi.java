package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.BrandManager;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.CardManager;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager.UserManager;

@RestController
@RequestMapping("/api/card")
public final class CardApi {
    private CardManager cardManager;
    private UserManager userMAnager;
    private BrandManager brandManager;

    @Autowired
    public CardApi(CardManager cardManager, UserManager userMAnager, BrandManager brandManager) {
        this.cardManager = cardManager;
        this.userMAnager = userMAnager;
        this.brandManager = brandManager;
    }

    @GetMapping("/all") // do usunieacia ----------------------------------------------------------------
    public Iterable<Card> getAll(@RequestHeader HttpHeaders headers) {
        return cardManager.findAll();
    }

    @PostMapping
    public Card add(@RequestBody Card card, @RequestHeader HttpHeaders headers) {
        System.out.println(card.toString());
        card.setBrand(brandManager.findById(1L).get());
        card.setUser(userMAnager.findById(1L).get());
        return cardManager.save(card);
    }

    @PutMapping
    public Card update(@RequestBody Card card, @RequestHeader HttpHeaders headers) {
        return cardManager.save(card);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id, @RequestHeader HttpHeaders headers) {
        cardManager.deleteById(id);
    }
}
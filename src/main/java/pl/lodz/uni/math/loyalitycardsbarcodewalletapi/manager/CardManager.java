package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.CardRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;

import java.util.Optional;

@Service
public final class CardManager {
    private CardRepo cardRepo;

    @Autowired
    public CardManager(CardRepo cardRepo) {
        this.cardRepo = cardRepo;
    }

    public Optional<Card> findById(Long id) {
        return cardRepo.findById(id);
    }

    public Iterable<Card> findAll() {
        return cardRepo.findAll();
    }

    public Card save(Card card) {
        return cardRepo.save(card);
    }

    public void deleteById(Long id) {
        cardRepo.deleteById(id);
    }
}
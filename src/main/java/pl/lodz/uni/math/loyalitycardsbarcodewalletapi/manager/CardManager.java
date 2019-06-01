package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.BrandRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.CardRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.UserRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;

import java.util.Optional;

@Service
public final class CardManager {
    private CardRepo cardRepo;
    private UserRepo userRepo;
    private BrandRepo brandRepo;

    @Autowired
    public CardManager(CardRepo cardRepo, UserRepo userRepo, BrandRepo brandRepo) {
        this.cardRepo = cardRepo;
        this.userRepo = userRepo;
        this.brandRepo = brandRepo;
    }

    public Optional<Card> findById(Long id) {
        return cardRepo.findById(id);
    }

    public Iterable<Card> findAll() {
        return cardRepo.findAll();
    }

    public Card save(Card card) {
        card.setUser(userRepo.save(card.getUser()));
        card.setBrand(brandRepo.save(card.getBrand()));
        return cardRepo.save(card);
    }

    public void deleteById(Long id) {
        cardRepo.deleteById(id);
    }
}
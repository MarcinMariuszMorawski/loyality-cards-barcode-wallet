package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api.exceptions.BadRequestException;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.CardRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.User;

import java.util.Optional;

@Service
public final class CardManager {
    private CardRepo cardRepo;
    private UserManager userManager;
    private BrandManager brandManager;

    @Autowired
    public CardManager(CardRepo cardRepo, UserManager userManager, BrandManager brandManager) {
        this.cardRepo = cardRepo;
        this.userManager = userManager;
        this.brandManager = brandManager;
    }

    public Optional<Card> findById(Long id) {
        return cardRepo.findById(id);
    }

    public Iterable<Card> findAll() {
        return cardRepo.findAll();
    }

    public Iterable<Card> findAllByUser(User user) {
        return cardRepo.findAllByUser(user);
    }

    public Iterable<Card> findAllUserCards(HttpHeaders headers) {
        if (!userManager.findByHeaders(headers).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        User user = userManager.findByHeaders(headers).get();

        return findAllByUser(user);
    }

    public Card save(Card card) {
        return cardRepo.save(card);
    }

    public Card saveUserCard(Card card, Long brandId, HttpHeaders headers) {
        if (!userManager.findByHeaders(headers).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        if (!brandManager.findById(brandId).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        card.setBrand(brandManager.findById(brandId).get());
        card.setUser(userManager.findByHeaders(headers).get());

        return save(card);
    }

    public void deleteById(Long id) {
        cardRepo.deleteById(id);
    }

    public void deleteUserCardById(Long id, HttpHeaders headers) {
        if (!userManager.findByHeaders(headers).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        User user = userManager.findByHeaders(headers).get();

        if (!findById(id).isPresent()) {
            throw new BadRequestException("Wrong brand id parameter");
        }

        Card card = findById(id).get();

        if (!card.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("You can not delete this card. It not belongs to you.");
        }
        deleteById(id);
    }
}
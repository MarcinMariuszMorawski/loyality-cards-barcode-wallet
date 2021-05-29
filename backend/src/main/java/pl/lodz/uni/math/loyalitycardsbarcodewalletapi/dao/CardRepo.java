package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.Card;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.User;

@Repository
public interface CardRepo extends CrudRepository<Card, Long> {
    Iterable<Card> findAllByUser(User user);
}
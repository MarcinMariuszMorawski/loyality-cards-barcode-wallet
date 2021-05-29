package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.User;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
package pl.lodz.uni.math.loyalitycardsbarcodewalletapi.manager;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.api.exceptions.BadRequestException;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.UserRepo;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.dao.entity.User;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtTokenService;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUser;
import pl.lodz.uni.math.loyalitycardsbarcodewalletapi.security.service.JwtUserChangePassword;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public final class UserManager {
    private UserRepo userRepo;
    private JwtTokenService jwtTokenService;

    @Autowired
    public UserManager(UserRepo userRepo, JwtTokenService jwtTokenService) {
        this.userRepo = userRepo;
        this.jwtTokenService = jwtTokenService;
    }

    public Optional<User> findById(Long id) {
        return userRepo.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Optional<User> findByHeaders(HttpHeaders headers) {

        String token = jwtTokenService.getTokenFromHeaders(headers);

        if (token == null) {
            throw new BadRequestException("Token validate error");
        }

        String email = jwtTokenService.getEmailFromToken(token);

        if (!findByEmail(email).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        return findByEmail(email);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public User save(User user) {
        user.setEmail(StringUtils.uncapitalize(user.getEmail()));
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }

    public void createAccount(JwtUser jwtUser) {
        if (jwtUser.getPassword().length() != 64) {
            throw new BadRequestException("Wrong new password");
        }

        if (findByEmail(jwtUser.getEmail()).isPresent()) {
            throw new BadRequestException("Email already used");
        }

        User user = new User(jwtUser.getEmail(), jwtUser.getPassword(), new Timestamp(System.currentTimeMillis()), true);
        save(user);
    }

    public void changePassword(JwtUserChangePassword jwtUserChangePassword, HttpHeaders headers) {

        if (jwtUserChangePassword.getNewPassword().length() != 64) {
            throw new BadRequestException("Wrong new password");
        }

        if (!findByHeaders(headers).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        User user = findByHeaders(headers).get();

        if (!user.getPassword().equals(jwtUserChangePassword.getOldPassword())) {
            throw new BadRequestException("Wrong old password");
        }

        user.setPassword(jwtUserChangePassword.getNewPassword());
        user.setDateTimeOfLastPasswordChange(new Timestamp(System.currentTimeMillis()));

        save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        save(new User("1", "6b86b273ff34fce19d6b804eff5a3f5747ada4eaa22f1d49c01e52ddb7875b4b", Timestamp.valueOf(LocalDateTime.now()), true));
    }
}
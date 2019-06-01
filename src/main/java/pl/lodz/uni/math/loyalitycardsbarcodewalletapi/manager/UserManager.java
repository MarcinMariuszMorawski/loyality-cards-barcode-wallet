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

    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public Optional<User> findByHeaders(HttpHeaders headers) {

        String token = jwtTokenService.getTokenFromHeader(headers);

        if (token == null) {
            throw new BadRequestException("Token validate error");
        }

        String login = jwtTokenService.getUsernameFromToken(token);

        if (!findByLogin(login).isPresent()) {
            throw new BadRequestException("Token validate error");
        }

        return findByLogin(login);
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public User save(User user) {
        user.setLogin(StringUtils.uncapitalize(user.getLogin()));
        return userRepo.save(user);
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
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

        save(user);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fill() {
        save(new User("1", "1", Timestamp.valueOf(LocalDateTime.now()), true));
    }


}

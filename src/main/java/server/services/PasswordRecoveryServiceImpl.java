package server.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.DTOs.PasswordRecoveryTransport;
import server.DTOs.PasswordRecoveryLinkValidationTransport;
import server.models.PasswordHistory;
import server.models.PasswordRecovery;
import server.models.User;
import server.password_recovery_utils.PasswordRecoveryLinkValidator;
import server.repositories.PasswordHistoryRepository;
import server.repositories.PasswordRecoveryRepo;
import server.repositories.UserRepo;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

    private static final int NUMBER_OF_LAST_PASSWORDS = 3;

    private UserRepo userRepo;
    private PasswordRecoveryRepo passwordRecoveryRepo;
    private PasswordHistoryRepository passwordHistoryRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public PasswordRecoveryServiceImpl(UserRepo userRepo, PasswordRecoveryRepo passwordRecoveryRepo,
                                       PasswordHistoryRepository passwordHistoryRepository,
                                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.passwordRecoveryRepo = passwordRecoveryRepo;
        this.passwordHistoryRepository = passwordHistoryRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void requestPasswordRecovery(String email) {
        String emailContent = "";
        User user = userRepo.findByEmail(email);
        if(user != null) {
            PasswordRecovery passwordRecovery = new PasswordRecovery(UUID.randomUUID().toString(), user,
                    new Timestamp(System.currentTimeMillis()), false);
            PasswordRecovery savedPasswordRecovery = passwordRecoveryRepo.save(passwordRecovery);
            String recoveryLink = "http://user/password-recoveries/{recoveryId}";
            emailContent = "Change password by clicking this link: " + recoveryLink;
        } else {
            emailContent = "The email is not linked to any user and password recovery cannot be done!";
        }
        // send email
    }

    @Override
    public PasswordRecoveryLinkValidationTransport validatePasswordRecoveryLink(String passwordRecoveryId) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepo.findById(passwordRecoveryId).orElseThrow(() -> new NoSuchElementException("Link not found"));
        PasswordRecoveryLinkValidator validator = new PasswordRecoveryLinkValidator(passwordRecovery);
        boolean isValid = validator.isValid();
        if(isValid) {
            passwordRecoveryRepo.updatePasswordRecoveryLinkStatus(passwordRecoveryId, true);
        }
        return new PasswordRecoveryLinkValidationTransport(isValid, validator.getValidationMessage());
    }

    @Override
    public PasswordRecoveryTransport saveNewPassword(String userId, String newPassword) {
        List<PasswordHistory> lastThreePasswords = passwordHistoryRepository.findLastNPasswordsByUser(userId, NUMBER_OF_LAST_PASSWORDS);
        if(lastThreePasswords.stream().map(PasswordHistory::getPassword).collect(Collectors.toList()).contains(newPassword)) {
            throw new RuntimeException("This password is present in the last " + NUMBER_OF_LAST_PASSWORDS + " passwords list!");
        }
        User user = userRepo.updateUserPassword(userId, bCryptPasswordEncoder.encode(newPassword));
        return new PasswordRecoveryTransport(user.getId(), user.getPassword());
    }

}

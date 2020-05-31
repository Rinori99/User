package server.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.DTOs.PasswordRecoveryTransport;
import server.DTOs.PasswordRecoveryLinkValidationTransport;
import server.integration.models.SerializableEmail;
import server.integration.producers.EmailProducer;
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
    private UserService userService;
    private PasswordRecoveryRepo passwordRecoveryRepo;
    private PasswordHistoryRepository passwordHistoryRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmailProducer emailProducer;

    public PasswordRecoveryServiceImpl(UserRepo userRepo, PasswordRecoveryRepo passwordRecoveryRepo,
                                       PasswordHistoryRepository passwordHistoryRepository,
                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                       EmailProducer emailProducer,
                                       UserService userService) {
        this.userRepo = userRepo;
        this.passwordRecoveryRepo = passwordRecoveryRepo;
        this.passwordHistoryRepository = passwordHistoryRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailProducer = emailProducer;
        this.userService = userService;
    }

    @Override
    public void requestPasswordRecovery(String email) {
        String emailContent = "";
        User user = userRepo.findByEmail(email);
        if(user != null) {
            PasswordRecovery passwordRecovery = new PasswordRecovery(UUID.randomUUID().toString(), user,
                    new Timestamp(System.currentTimeMillis()), false);
            PasswordRecovery savedPasswordRecovery = passwordRecoveryRepo.save(passwordRecovery);
            String recoveryLink = "/auth/forgot-password/" + passwordRecovery.getId();
            emailContent = "Change password by clicking this link: " + recoveryLink;
        } else {
            emailContent = "The email is not linked to any user and password recovery cannot be done!";
        }
        //emailProducer.produce(new SerializableEmail(email, "Password Recovery", emailContent));
    }

    @Override
    public PasswordRecoveryLinkValidationTransport validatePasswordRecoveryLink(String passwordRecoveryId) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepo.findById(passwordRecoveryId).orElseThrow(() -> new NoSuchElementException("Link not found"));
        PasswordRecoveryLinkValidator validator = new PasswordRecoveryLinkValidator(passwordRecovery);
        boolean isValid = validator.isValid();
        String validationMessage = validator.getValidationMessage();
        if(isValid) {
            passwordRecovery.setUsed(true);
            passwordRecoveryRepo.save(passwordRecovery);
            return new PasswordRecoveryLinkValidationTransport(isValid, validationMessage);
        } else {
            throw new RuntimeException(validationMessage);
        }
    }

    @Override
    public PasswordRecoveryTransport saveNewPassword(String userId, String newPassword) {
        String encryptedNewPassword = bCryptPasswordEncoder.encode(newPassword);
        User user = userRepo.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        List<PasswordHistory> lastThreePasswords = passwordHistoryRepository.findLastNPasswordsByUser(user.getId(), NUMBER_OF_LAST_PASSWORDS);
        if(lastThreePasswords.stream().map(PasswordHistory::getPassword).collect(Collectors.toList()).contains(encryptedNewPassword)) {
            throw new RuntimeException("This password is present in the last " + NUMBER_OF_LAST_PASSWORDS + " passwords list!");
        }
        user.setPassword(encryptedNewPassword);
        User savedUser = userRepo.save(user);
        passwordHistoryRepository.save(new PasswordHistory(UUID.randomUUID().toString(), savedUser, encryptedNewPassword, new Timestamp(System.currentTimeMillis())));
        return new PasswordRecoveryTransport(savedUser.getId(), savedUser.getPassword());
    }

}

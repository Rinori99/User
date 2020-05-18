package server.services;

import server.DTOs.PasswordRecoveryTransport;
import server.DTOs.PasswordRecoveryLinkValidationTransport;

public interface PasswordRecoveryService {

    void requestPasswordRecovery(String email);

    PasswordRecoveryLinkValidationTransport validatePasswordRecoveryLink(String passwordRecoveryId);

    PasswordRecoveryTransport saveNewPassword(String userId, String newPassword);

}

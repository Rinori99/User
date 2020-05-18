package server.password_recovery_utils;

import server.models.PasswordRecovery;

public class PasswordRecoveryLinkValidator {

    private static final long PASSWORD_RECOVERY_LINK_EXPIRATION_TIME = 600_000; //milliseconds
    private PasswordRecovery passwordRecovery;

    public PasswordRecoveryLinkValidator(PasswordRecovery passwordRecovery) {
        this.passwordRecovery = passwordRecovery;
    }

    public String getValidationMessage() {
        String message = "";
        if(hasExpired()) {
            message = "This password recovery link has expired and is not valid anymore";
        } else if(passwordRecovery.isUsed()) {
            message = "This password recovery link has been used before and is not valid anymore";
        } else {
            message = "The password recovery link is valid";
        }
        return message;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() - passwordRecovery.getDateTimeRequested().getTime() > PASSWORD_RECOVERY_LINK_EXPIRATION_TIME;
    }

    public boolean isValid() {
        return !(hasExpired() || passwordRecovery.isUsed());
    }

}

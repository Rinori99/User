package server.DTOs;

public class PasswordRecoveryLinkValidationTransport {

    private boolean isValid;
    private String message;

    public PasswordRecoveryLinkValidationTransport(boolean isValid) {
        this.isValid = isValid;
    }

    public PasswordRecoveryLinkValidationTransport(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

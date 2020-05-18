package server.DTOs;

public class PasswordRecoveryTransport {

    private String userId;
    private String newPassword;

    public PasswordRecoveryTransport() {

    }

    public PasswordRecoveryTransport(String userId, String newPassword) {
        this.userId = userId;
        this.newPassword = newPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}

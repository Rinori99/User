package server.security;

public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String PASSWORD_RECOVERY_URL = "/password-recoveries*";
    public static final String PASSWORD_RECOVERY_GENERAL = "/password-recoveries/*";
    public static final String REGISTER_URL = "/users";

}

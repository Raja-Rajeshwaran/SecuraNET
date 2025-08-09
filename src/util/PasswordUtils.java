package util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    public static String maskPassword(String password) {
        if (password == null) {
            return null;
        }
        return "*".repeat(password.length());
    }
    
    public static String maskPasswordPartial(String password, int visibleChars) {
        if (password == null || password.length() <= visibleChars) {
            return maskPassword(password);
        }
        
        String visiblePart = password.substring(0, visibleChars);
        String maskedPart = "*".repeat(password.length() - visibleChars);
        return visiblePart + maskedPart;
    }
}
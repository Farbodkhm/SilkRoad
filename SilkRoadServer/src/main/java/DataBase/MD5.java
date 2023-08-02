package DataBase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    public static String creatPassword(String originalPassword) {
        final StringBuilder hexString = new StringBuilder();
        try{
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(originalPassword.getBytes(StandardCharsets.UTF_8));
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
        } catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        }
        return hexString.toString();
    }
}

package ca.bcit.comp2522.termprojec.olu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Hashes Password for security.
 * @author Urjit, Leo
 * @version 2022
 */
public class PasswordHash {
    /**
     * Encrypts password given.
     * @param passwordToHash Password to hash
     * @return Hashed password
     */
    public static String encrypt(final String passwordToHash) {
        String generatedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

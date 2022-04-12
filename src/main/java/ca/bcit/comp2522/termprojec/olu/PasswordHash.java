package ca.bcit.comp2522.termprojec.olu;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Hashes Password for security.
 * @author Urjit, Leo
 * @version 2022
 */
public final class PasswordHash {

    private static final int HEX_VALUE_ONE = 0xff;
    private static final int HEX_VALUE_TWO = 0x100;
    private static final int TO_STRING_RADIX = 16;
    private PasswordHash() {
    }
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
                sb.append(Integer.toString((aByte & HEX_VALUE_ONE) + HEX_VALUE_TWO, TO_STRING_RADIX).substring(1));
            }
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

package loanbook.model;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Model a password object, performs hash encryption and decryption.
 */
public class Password {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should be alphanumeric of at least 6 characters and up to 10 characters.\n";

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    private String password;

    public Password(String pass, String salt) {
        password = Password.encrypt(pass, salt);
    }

    /**
     * Generate salt for hashing.
     * @return salt key.
     */
    //@@author {wn96}-reused
    //http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
    public static String getSalt() {
        StringBuilder returnValue = new StringBuilder(KEY_LENGTH);
        for (int i = 0; i < KEY_LENGTH; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }
    //@@author

    /**
     * Algorithm to hash string.
     * @param password
     * @param salt
     * @return hashed passsword.
     */
    //@@author {wn96}-reused
    //http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
    //@@author

    //@@author {wn96}-reused
    //http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
    private static String generateSecurePassword(String password, String salt) {
        byte[] securePassword = hash(password.toCharArray(), salt.getBytes());

        return Base64.getEncoder().encodeToString(securePassword);
    }
    //@@author

    /**
     * Check if hashed currentPassword and hashed oldPassInput is same when decrypted.
     * @param currentPass The current password that was previously set.
     * @param oldPassInput Input to check if it is same as current password.
     * @return Boolean of whether input matches with current password.
     */
    public static boolean isSamePassword(String currentPass, String oldPassInput, String salt) {
        return verifyUserPassword(oldPassInput, currentPass, salt);
    }

    /**
     * Check if input password is the same as the current password.
     * @param providedPassword
     * @param securedPassword
     * @param salt
     * @return
     */
    //@@author {wn96}-reused
    //http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
    private static boolean verifyUserPassword(String providedPassword,
                                             String securedPassword, String salt) {
        boolean returnValue = false;

        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);

        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);

        return returnValue;
    }
    //@@author

    /**
     * Returns true if a given string is a valid password.
     */
    public static boolean isValidPass(String objString) {
        return objString.matches("^[a-zA-Z0-9]{6,10}$");
    }

    private static String encrypt(String pass, String salt) {
        return generateSecurePassword(pass, salt);
    }

    /**
     * For saving to database.
     * @return Hashed String
     */
    public String hashedPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && password.equals(((Password) other).password)); // state check
    }
}

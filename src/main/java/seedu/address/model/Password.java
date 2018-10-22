package seedu.address.model;

/**
 * Model a password object, performs hash encryption and decryption.
 */
public class Password {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "TO ADD CONSTRAINS";

    private String password;

    public Password(String pass) {
        password = Password.encrypt(pass);
    }

    /**
     * Check if hashed currentPassword and hashed oldPassInput is same when decrypted.
     * @param currentPass
     * @param oldPassInput
     * @return boolean of whether input matches with current password.
     */
    public static boolean isSamePassword(String currentPass, Password oldPassInput) {
        Password.decrypt(currentPass);
        oldPassInput.decrypt();
        return Password.decrypt(currentPass).equals(oldPassInput.decrypt());
    }

    /**
     * Returns true if a given string is a valid password
     */
    public static boolean isValidPass(String objString) {
        return true;
    }

    private static String decrypt(String pass) {
        return pass.substring(0, pass.length() - "-encrypt".length());
    }

    private String decrypt() {
        return password.substring(0, password.length() - "-encrypt".length());
    }

    private static String encrypt(String pass) {
        return pass + "-encrypt";
    }

    /**
     * For saving to database
     * @return Hashed String
     */
    public String hashedPassword() {
        return password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Password // instanceof handles nulls
                && decrypt(password).equals(((Password) other).decrypt())); // state check
    }
}

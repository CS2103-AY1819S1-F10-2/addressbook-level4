package seedu.address.model;

/**
 * Model a password object, performs hash encryption and decryption.
 */
public class Password {
    public String password;

    public Password(String pass) {
        password = pass;
    }

    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "TO ADD CONSTRAINS";

    public static boolean isSamePassword(String currentPass, Password oldPassInput) {
        return currentPass.equals(oldPassInput.password);
    }

    /**
     * Returns true if a given string is a valid password
     */
    public static boolean isValidPass(String objString) {
        return true;
    }

    @Override
    public String toString() {
        return password;
    }
}

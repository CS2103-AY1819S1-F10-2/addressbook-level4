package seedu.address.model;

/**
 * Model a password object, performs hash encryption and decryption.
 */
public class Password {
    private String password;

    public Password(String pass) {
        password = pass;
    }

    public static boolean isSamePassword(String currentPass, Password oldPassInput) {
        return currentPass.equals(oldPassInput.password);
    }

    @Override
    public String toString() {
        return password;
    }
}

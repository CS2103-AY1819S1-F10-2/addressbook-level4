package loanbook.model;

/**
 * Model a password object, performs hash encryption and decryption.
 */
public class Password {
    public static final String MESSAGE_PASSWORD_CONSTRAINTS =
            "Password should be alphanumeric of at least 6 characters and up to 10 characters.\n";

    private String password;

    public Password(String pass) {
        password = Password.encrypt(pass);
    }

    /**
     * Check if hashed currentPassword and hashed oldPassInput is same when decrypted.
     * @param currentPass The current password that was previously set.
     * @param oldPassInput Input to check if it is same as current password.
     * @return Boolean of whether input matches with current password.
     */
    public static boolean isSamePassword(String currentPass, Password oldPassInput) {
        String curr = Password.decrypt(currentPass);
        String toCheck = oldPassInput.decrypt();
        return curr.equals(toCheck);
    }

    /**
     * Returns true if a given string is a valid password
     */
    public static boolean isValidPass(String objString) {
        return objString.matches("^[a-zA-Z0-9]{6,10}$");
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

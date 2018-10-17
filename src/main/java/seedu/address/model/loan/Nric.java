package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Class that stores the NRIC of a person.
 */
public class Nric {

    public static final String MESSAGE_NRIC_CONSTRAINTS =
            "NRIC should be Singapore issued. It may be blank. ";

    public static final String NRIC_VALIDATION_REGEX = "^[ST]\\d{7}[A-JZ]|[FG]\\d{7}[K-NPQRTUWX]$";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param ic A valid nric.
     */
    public Nric(String ic) {
        requireNonNull(ic);
        ic = ic.trim().toUpperCase();
        checkArgument(isValidNric(ic), MESSAGE_NRIC_CONSTRAINTS);
        nric = ic;
    }

    /**
     * Returns true if a given string is a valid nric.
     * Precondition: test is not null.
     */
    public static boolean isValidNric(String test) {
        String ic = test.toUpperCase();

        if (!ic.matches(NRIC_VALIDATION_REGEX)) {
            return false;
        }

        String prefix = String.valueOf(ic.charAt(0));
        String checksum = String.valueOf(ic.charAt(8));
        String digits = ic.substring(1, 8);

        int[] weights = {2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        // Generate checksum
        for (int i = 0; i < digits.length(); i++) {
            sum += Integer.parseInt(String.valueOf(digits.charAt(i))) * weights[i];
        }

        // Add 4 to IC issued in 21th century. This rule was designed by the Singapore government.
        if ("G".equals(prefix) || "T".equals(prefix)) {
            sum += 4;
        }

        if ("S".equals(prefix) || "T".equals(prefix)) {
            String[] nricCheckDigits = {"J", "Z", "I", "H", "G", "F", "E", "D", "C", "B", "A"};
            return nricCheckDigits[sum % 11].equals(checksum);
        } else {
            String[] finCheckDigits = {"X", "W", "U", "T", "R", "Q", "P", "N", "M", "L", "K"};
            return finCheckDigits[sum % 11].equals(checksum);
        }
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && nric.equals(((Nric) other).nric)); // state check
    }

    @Override
    public int hashCode() {
        return nric.hashCode();
    }
}

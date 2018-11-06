package loanbook.model.loan;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.AppUtil.checkArgument;

/**
 * Class that stores the NRIC of a person.
 */
public class Nric implements Censor {

    public static final String MESSAGE_NRIC_CONSTRAINTS =
            "Invalid NRIC input. NRIC should be a valid Singapore-issued NRIC number consisting of a "
            + "prefix letter, followed by 7 numbers and a checksum suffix.";

    public static final String NRIC_VALIDATION_REGEX = "^[ST]\\d{7}[A-JZ]|[FG]\\d{7}[K-NPQRTUWX]$";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param ic A valid nric.
     */
    public Nric(String ic) {
        requireNonNull(ic);
        ic = ic.toUpperCase();
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

    @Override
    public String getCensored() {
        String censorPart = doCensoring(this.nric.length() - 4);
        return this.nric.charAt(0) + censorPart + this.nric.substring(6);
    }

    @Override
    public String doCensoring(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('x');
        }
        return sb.toString();
    }
}

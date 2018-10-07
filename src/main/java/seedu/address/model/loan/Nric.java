package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class that stores the NRIC of a person.
 */
public class Nric {

    public static final String MESSAGE_Nric_CONSTRAINTS =
            "Nric should be singapore issued. It may be blank. ";

    public static final String Nric_VALIDATION_REGEX = "^[STGF][0-9]{7}[A-Z]$";

    public final String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param ic A valid nric.
     */
    public Nric(String ic) {
        ic = ic.trim().toUpperCase();
        requireNonNull(ic);
        checkArgument(isValidNric(ic), MESSAGE_Nric_CONSTRAINTS);
        nric = ic;
    }

    /**
     * Returns true if a given string is a valid nric.
     */
    public static boolean isValidNric(String test) {
        String ic = test.trim().toUpperCase();
        return ic.matches(Nric_VALIDATION_REGEX) && correctChecksum(ic);
    }

    /**
     * Check if Nric input matches checksum provided by the Singapore Government.
     * @param ic
     * @return Boolean if valid Nric.
     */
    private static boolean correctChecksum(String ic) {
        String[] validPrefix = {"S", "T", "G", "F"};
        Stream<String> icPrefixStream = Arrays.stream(validPrefix);

        if (ic == null || ic.length() != 9 || icPrefixStream.noneMatch(String.valueOf(ic.charAt(0))::equals)) {
            return false;
        }

        String prefix = String.valueOf(ic.charAt(0));
        String checksum = String.valueOf(ic.charAt(8));
        String digits = ic.substring(1, 8);

        int[] weights = {2, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        if (Double.isNaN(Integer.valueOf(digits))) {
            return false;
        }

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

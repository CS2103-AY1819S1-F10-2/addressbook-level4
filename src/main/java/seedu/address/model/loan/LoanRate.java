package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Loan's rate in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRate}
 */
public class LoanRate {

    public static final String LOANRATE_VALIDATION_REGEX =
            "^(([1-9]\\d*|0)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$";
    public static final String MESSAGE_LOANRATE_CONSTRAINTS =
            "Rate should be not less than 0 and have at most 2 decimal places.";

    public final double rate;

    /**
     * Constructs a {@code LoanRate}.
     *
     * @param rate A valid rate.
     */
    LoanRate(String rate) {
        requireNonNull(rate);
        checkArgument(isValidRate(rate), MESSAGE_LOANRATE_CONSTRAINTS);
        this.rate = Double.parseDouble(rate);
    }

    /**
     * Returns true if a given string is a valid rate.
     */
    public static boolean isValidRate(String test) {
        return test.matches(LOANRATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return String.valueOf(rate);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanRate // instanceof handles nulls
                && rate == ((LoanRate) other).rate); // state check
    }

    @Override
    public int hashCode() {
        return Double.hashCode(rate);
    }
}

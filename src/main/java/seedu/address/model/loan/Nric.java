package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's NRIC number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Nric extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS =
        "NRIC numbers should start with a S, T, F or G, followed by 7 decimal digits, then end with a letter.";

    public static final Predicate<String> isValid = test ->
        test.matches("[STFGstfg]\\d{7}[A-Za-z]");

    /**
     * Constructs a {@code Nric}.
     *
     * @param objString A valid objString for a NRIC number.
     */
    public Nric(String objString) {
        super(MESSAGE_CONSTRAINTS, isValid, str -> str.toUpperCase(), objString);
    }
}

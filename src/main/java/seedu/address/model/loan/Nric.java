package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's NRIC number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Nric extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS =
        "NRIC numbers should start with a S, T, F or G, followed by 7 decimal digits, then end with a capital letter.";

    public static final Predicate<String> isValid = test ->
        test.matches("[STFG]\\d{7}[A-Z]");

    /**
     * Constructs a {@code Nric}.
     *
     * @param objString A valid objString for a NRIC number.
     */
    public Nric(String objString) {
        super(MESSAGE_CONSTRAINTS, isValid, Function.identity(), objString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Nric // instanceof handles nulls
            && value.equals(((Nric) other).value)); // state check
    }
}

package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Name extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final Predicate<String> isValid = test ->
        test.matches("[\\p{Alnum}][\\p{Alnum} ]*");

    /**
     * Constructs a {@code Name}.
     *
     * @param value A valid name.
     */
    public Name(String value) {
        super(MESSAGE_CONSTRAINTS, isValid, Function.identity(), value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && value.equals(((Name) other).value)); // state check
    }
}

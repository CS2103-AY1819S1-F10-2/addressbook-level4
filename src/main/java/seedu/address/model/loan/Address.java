package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Address extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final Predicate<String> isValid = test ->
        test.matches("[^\\s].*");

    /**
     * Constructs an {@code Address}.
     *
     * @param objString A valid objString for an address.
     */
    public Address(String objString) {
        super(MESSAGE_CONSTRAINTS, isValid, Function.identity(), objString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

}

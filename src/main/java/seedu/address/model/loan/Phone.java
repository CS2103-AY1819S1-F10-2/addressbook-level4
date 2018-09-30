package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Phone extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";

    public static final Predicate<String> isValid = test ->
        test.matches("\\d{3,}");

    /**
     * Constructs a {@code Phone}.
     *
     * @param objString A valid objString for a phone number.
     */
    public Phone(String objString) {
        super(MESSAGE_CONSTRAINTS, isValid, Function.identity(), objString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }
}

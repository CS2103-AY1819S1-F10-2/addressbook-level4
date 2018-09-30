package seedu.address.model.tag;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.loan.LoanField;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValid}
 */
public class Tag extends LoanField<String> {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";

    public static final Predicate<String> isValid = test ->
        test.matches("\\p{Alnum}+");

    /**
     * Constructs a {@code Tag}.
     *
     * @param objString A valid objString for a tag name.
     */
    public Tag(String objString) {
        super(MESSAGE_CONSTRAINTS, isValid, Function.identity(), objString);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && value.equals(((Tag) other).value)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + value + ']';
    }

}

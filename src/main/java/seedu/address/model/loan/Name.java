package seedu.address.model.loan;

import java.util.function.Predicate;

/**
 * Represents a Loan's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValid}
 */
public class Name extends LoanField {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    /**
     * Returns true if the given string is a valid name, as specified by the validation regex.
     */
    public static final Predicate<String> isValid = test -> test.matches(VALIDATION_REGEX);

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(MESSAGE_CONSTRAINTS, isValid, name);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && value.equals(((Name) other).value)); // state check
    }
}

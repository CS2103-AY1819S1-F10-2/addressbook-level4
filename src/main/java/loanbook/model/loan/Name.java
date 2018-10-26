package loanbook.model.loan;

import java.util.function.Function;

/**
 * Represents a Loan's name in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends DataField<String> {

    public static final String MESSAGE_NAME_CONSTRAINTS =
        "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(MESSAGE_NAME_CONSTRAINTS, Name::isValidName, Function.identity(), name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String objString) {
        /*
         * The first character of the name must not be a whitespace,
         * otherwise " " (a blank string) becomes a valid input.
         */
        return objString.matches("[\\p{Alnum}][\\p{Alnum} ]*");
    }

}

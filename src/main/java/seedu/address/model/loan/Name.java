package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name extends DataField<String> {

    public static final String MESSAGE_NAME_CONSTRAINTS =
        "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final Predicate<String> VALIDITY_PREDICATE =
        test -> test.matches("[\\p{Alnum}][\\p{Alnum} ]*");

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        super(MESSAGE_NAME_CONSTRAINTS, VALIDITY_PREDICATE, Function.identity(), name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String objString) {
        return VALIDITY_PREDICATE.test(objString);
    }

}

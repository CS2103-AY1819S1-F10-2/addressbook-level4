package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address extends DataField<String> {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final Predicate<String> VALIDITY_PREDICATE =
        test -> test.matches("[^\\s].*");

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        super(MESSAGE_ADDRESS_CONSTRAINTS, VALIDITY_PREDICATE, Function.identity(), address);
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String objString) {
        return VALIDITY_PREDICATE.test(objString);
    }

}

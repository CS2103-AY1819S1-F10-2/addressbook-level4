package seedu.address.model.loan;

import java.util.function.Function;

/**
 * Represents a Loan's address in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidAddress(String)}
 */
public class Address extends DataField<String> {

    public static final String MESSAGE_ADDRESS_CONSTRAINTS =
            "Addresses can take any values, and it should not be blank";

    /**
     * Constructs an {@code Address}.
     *
     * @param address A valid address.
     */
    public Address(String address) {
        super(MESSAGE_ADDRESS_CONSTRAINTS, Address::isValidAddress, Function.identity(), address);
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String objString) {
        /*
         * The first character of the address must not be a whitespace,
         * otherwise " " (a blank string) becomes a valid input.
         */
        return objString.matches("[^\\s].*");
    }

}

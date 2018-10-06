package seedu.address.model.loan;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a Loan's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone extends DataField<String> {

    public static final String MESSAGE_PHONE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";

    public static final Predicate<String> VALIDITY_PREDICATE =
        test -> test.matches("\\d{3,}");

    /**
     * Constructs a {@code Phone}.
     *
     * @param phone A valid phone number.
     */
    public Phone(String phone) {
        super(MESSAGE_PHONE_CONSTRAINTS, VALIDITY_PREDICATE, Function.identity(), phone);
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String objString) {
        return VALIDITY_PREDICATE.test(objString);
    }

}

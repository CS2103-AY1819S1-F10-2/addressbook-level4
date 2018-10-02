package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a generic field in the Loan class.
 * T: The type of the underlying value this LoanField has.
 * Guarantees: immutable; is valid as per the isValid function passed to the constructor.
 */
public abstract class LoanField<T> {

    /**
     * Subclasses should have:
     * - public static final String MESSAGE_CONSTRAINTS = [string];
     *      A message to the user describing how a valid objString should be formatted.
     * - public static final Predicate<String> isValid = test -> test.matches([regex-expression]);
     *      Returns true iff the given objString is valid.
     * - The constructor of the subclass only needs to be of this format:
     *     - arguments: String objString
     *     - { super(MESSAGE_CONSTRAINTS, isValid, [specify parser, e.g. Integer.parseInt], objString); }
     *         - The specified parser must be guaranteed to work (i.e. output a valid value) on a valid objString
     * - toString() may be further overridden as necessary, e.g. for display purposes, or for
     *   converting the value back to a valid objString.
     */

    public final T value;

    /**
     * Constructs a {@code LoanField}.
     *
     * @param msgConstraints A message to the user describing how a valid objString should be formatted.
     * @param isValid A predicate to check if the given objString is valid.
     * @param parser A function to convert the given objString to a value of type T.
     * @param objString A non-null and valid string describing the value.
     */
    public LoanField(String msgConstraints, Predicate<String> isValid, Function<String,T> parser, String objString) {
        requireNonNull(objString);
        checkArgument(isValid.test(objString), msgConstraints);
        this.value = parser.apply(objString);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }
        if (other == null) {
            return false; // null objects are not equal to this object (which is non-null)
        }
        // Returns true if both objects are of the same class and holding the same value
        return other.getClass() == this.getClass()
                && this.value.equals(((LoanField)other).value);
    }
}

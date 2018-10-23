package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents a generic field that is directly describable as a string (called an objString).
 * T: The type of the underlying value this DataField has.
 * Guarantees: immutable; is valid as per the isValid function passed to the constructor.
 */
public abstract class DataField<T> {

    /**
     * Subclasses should have:
     * - {@code static final String MESSAGE_CONSTRAINTS = [string];}
     *      A message to the user describing how a valid objString should be formatted.
     * - {@code public static boolean isValid(String objString) {...}}
     *      Returns true iff the given objString is valid.
     * - The constructor only needs to be of this format:
     *     - arguments: {@code String objString}
     *     - {@code { super(MESSAGE_CONSTRAINTS, [CLASS-NAME]::isValid, parser, objString); }}
     *         - The parser converts the given objString into an object of type {@code T}, e.g. {@code Integer.parseInt}
     *         - The parser must be guaranteed to work (i.e. output a valid value) on a valid objString
     */

    public final T value;

    /**
     * Constructs a {@code LoanField}.
     *
     * @param msgConstraints A message to the user describing how a valid objString should be formatted.
     * @param validityPred A predicate to check if the given objString is valid.
     *                     Should typically be {@code [CLASS-NAME]::isValid}.
     * @param parser A function to convert the given objString to a value of type T.
     * @param objString A non-null and valid string describing the value.
     */
    public DataField(
        String msgConstraints,
        Predicate<String> validityPred,
        Function<String, T> parser,
        String objString) {

        requireNonNull(objString);
        checkArgument(validityPred.test(objString), msgConstraints);
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
            && this.value.equals(((DataField) other).value);
    }
}

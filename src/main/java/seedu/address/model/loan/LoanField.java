package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.function.Predicate;

/**
 * Represents a field in the Loan class.
 * Guarantees: immutable; is valid as per the isValid function passed to the constructor.
 */
public abstract class LoanField { // TODO: use a genetic T for the value type. To make it easier to manipulate numbers for example.

    public final String value;

    /**
     * Constructs a {@code LoanField}.
     *
     * @param msgConstraints A message to the user describing what a valid value is like.
     * @param isValid A function to check if the given value is valid.
     * @param value A non-null and valid value.
     */
    public LoanField(String msgConstraints, Predicate<String> isValid, String value) {
        requireNonNull(value);
        checkArgument(isValid.test(value), msgConstraints);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

package seedu.address.model.loan;

/**
 * Represents a Loan's ID in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLoanId(String)}
 */
public class LoanId extends DataField<Integer> {
    public static final String MESSAGE_LOANID_CONSTRAINTS = "Loan ID must be a non-negative integer with at least 1 "
            + "digit and no more than 9 digits.";

    /** A pattern that specifies "between 1 to 9 digits". */
    public static final String VALIDATION_REGEX = "^[0-9]{1,9}";

    public static final int MINIMUM_ID = 0;
    public static final int MAXIMUM_ID = 999999999;

    /**
     * Constructs a {@code LoanId}.
     *
     * @param loanId A valid Loan ID.
     */
    public LoanId(String loanId) {
        super(MESSAGE_LOANID_CONSTRAINTS, LoanId::isValidLoanId, Integer::parseInt, loanId);
    }

    /**
     * Creates a new Loan ID from an integer.
     *
     * @param value The value of the Loan ID.
     * @return A LoanID object with the specified integer as the value.
     */
    public static LoanId fromInt(int value) {
        return new LoanId(Integer.toString(value));
    }

    /**
     * Checks if a given string is a valid Loan ID.
     * A valid Loan ID is a non-negative integer with between 1 to 9 digits inclusive.
     *
     * @param objString The string to test.
     * @return true if the specified string is a valid Loan ID.
     */
    public static boolean isValidLoanId(String objString) {
        return objString.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if a given integer is a valid Loan ID.
     * A valid Loan ID is a non-negative integer with between 1 to 9 digits inclusive.
     *
     * @param value The integer to test.
     * @return true if the specified integer is a valid Loan ID.
     */
    public static boolean isValidLoanId(int value) {
        return MINIMUM_ID <= value && value <= MAXIMUM_ID;
    }

    /**
     * Checks if this Loan ID is the maximum possible Loan ID.
     *
     * @return true if this Loan ID is the maximum possible Loan ID.
     */
    public boolean isMaximumId() {
        return value == MAXIMUM_ID;
    }
}

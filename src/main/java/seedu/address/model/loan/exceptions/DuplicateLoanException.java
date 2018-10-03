package seedu.address.model.loan.exceptions;

/**
 * Signals that the operation will result in duplicate Loans (Loans are considered duplicates if they have the same
 * identity).
 */
public class DuplicateLoanException extends RuntimeException {
    public DuplicateLoanException() {
        super("Operation would result in duplicate loans");
    }
}

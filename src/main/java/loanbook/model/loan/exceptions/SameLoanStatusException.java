package loanbook.model.loan.exceptions;

/**
 * Signals that the operation will result in duplicate Loans (Loans are considered duplicates if they have the same
 * identity).
 */
public class SameLoanStatusException extends RuntimeException {
    public SameLoanStatusException() {
        super("Current loan has the same loan status!");
    }
}

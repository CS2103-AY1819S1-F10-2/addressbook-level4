package seedu.address.model.loan;

import seedu.address.model.UniqueList;
import seedu.address.model.loan.exceptions.DuplicateLoanException;
import seedu.address.model.loan.exceptions.LoanNotFoundException;

/**
 * A UniqueList of loans.
 */
public class UniqueLoanList extends UniqueList<Loan> {

    protected void throwDuplicateException() throws DuplicateLoanException {
        throw new DuplicateLoanException();
    }

    protected void throwNotFoundException() throws LoanNotFoundException {
        throw new LoanNotFoundException();
    }
}

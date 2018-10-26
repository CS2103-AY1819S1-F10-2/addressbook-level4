package loanbook.model.loan;

import loanbook.model.UniqueList;
import loanbook.model.loan.exceptions.DuplicateLoanException;
import loanbook.model.loan.exceptions.LoanNotFoundException;

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

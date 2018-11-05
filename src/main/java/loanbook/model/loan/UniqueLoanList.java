package loanbook.model.loan;

import java.util.Optional;

import loanbook.model.UniqueList;
import loanbook.model.loan.exceptions.DuplicateLoanException;
import loanbook.model.loan.exceptions.LoanNotFoundException;

/**
 * A UniqueList of loans.
 */
public class UniqueLoanList extends UniqueList<Loan> {

    /**
     * Returns the loan whose Loan ID matches the specified Loan ID, if it exists.
     */
    public Optional<Loan> getLoanById(LoanId loanId) {
        return internalList.stream()
                .filter(loan -> loan.getLoanId().equals(loanId))
                .findFirst();
    }

    protected void throwDuplicateException() throws DuplicateLoanException {
        throw new DuplicateLoanException();
    }

    protected void throwNotFoundException() throws LoanNotFoundException {
        throw new LoanNotFoundException();
    }
}

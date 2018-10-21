package seedu.address.testutil;

import seedu.address.model.LoanBook;
import seedu.address.model.loan.Loan;

public class TypicalLoanBook {
    /**
     * Returns an {@code LoanBook} with all the typical loans.
     */
    public static LoanBook getTypicalLoanBook() {
        LoanBook ab = new LoanBook();
        for (Loan loan : TypicalLoans.getTypicalLoans()) {
            ab.addLoan(loan);
        }
        return ab;
    }
}

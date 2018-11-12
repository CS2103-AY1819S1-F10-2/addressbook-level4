package loanbook.testutil;

import loanbook.model.LoanBook;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanIdManager;

/**
 * A utility class containing a factory for a {@code LoanBook} object to be used in tests.
 */
public class TypicalLoanBook {

    /**
     * Returns an {@code LoanBook} with all the typical bikes and loans.
     */
    public static LoanBook getTypicalLoanBook() {
        LoanBook lb = new LoanBook();
        for (Bike bike : TypicalBikes.getTypicalBikes()) {
            lb.addBike(bike);
        }
        for (Loan loan : TypicalLoans.getTypicalLoans()) {
            lb.addLoan(loan);
        }

        int lastUsedId = lb.getLoanList().size() + LoanId.MINIMUM_ID - 1;
        LoanId lastUsedLoanId = LoanId.isValidLoanId(lastUsedId) ? LoanId.fromInt(lastUsedId) : null;

        lb.setLoanIdManager(new LoanIdManager(lastUsedLoanId));

        return lb;
    }

    /**
     * Returns an {@code LoanBook} with one not returned loan.
     */
    public static LoanBook getLoanBookWithUnreturnedLoans() {
        LoanBook lb = new LoanBook();
        for (Bike bike : TypicalBikes.getTypicalBikes()) {
            lb.addBike(bike);
        }

        lb.addLoan(new LoanBuilder().build());
        lb.addLoan(new LoanBuilder().withName("Ash").withLoanStatus("RETURNED").build());

        int lastUsedId = lb.getLoanList().size() + LoanId.MINIMUM_ID - 1;
        LoanId lastUsedLoanId = LoanId.isValidLoanId(lastUsedId) ? LoanId.fromInt(lastUsedId) : null;
        lb.setLoanIdManager(new LoanIdManager(lastUsedLoanId));

        return lb;
    }
}

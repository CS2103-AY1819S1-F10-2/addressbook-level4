package seedu.address.testutil;

import seedu.address.model.LoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

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
        return lb;
    }
}

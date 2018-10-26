package seedu.address.testutil;

import seedu.address.model.LoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanIdManager;

/**
 * A utility class containing a factory for a {@code LoanBook} object to be used in tests.
 */
public class TypicalLoanBook {

    public static final LoanId LAST_USED_LOAN_ID = LoanId.fromInt(765);

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

        lb.setLoanIdManager(new LoanIdManager(LAST_USED_LOAN_ID));
        return lb;
    }
}

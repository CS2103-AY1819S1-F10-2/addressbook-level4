package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanIdManager;

/**
 * Unmodifiable view of an loan book
 */
public interface ReadOnlyLoanBook {

    /**
     * Returns an unmodifiable view of the bikes list.
     * This list will not contain any duplicate bikes.
     */
    ObservableList<Bike> getBikeList();

    /**
     * Returns an unmodifiable view of the loans list.
     * This list will not contain any duplicate loans.
     */
    ObservableList<Loan> getLoanList();

    /**
     * Returns a copy of the Loan ID Manager.
     * // TODO: Change this to an unmodifiable view
     */
    LoanIdManager getLoanIdManager();

}

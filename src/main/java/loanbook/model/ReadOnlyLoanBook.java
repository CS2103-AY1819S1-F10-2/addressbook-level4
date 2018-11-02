package loanbook.model;

import javafx.collections.ObservableList;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanIdManager;

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
     */
    LoanIdManager getLoanIdManager();

    /**
     * Checks if this ReadOnlyLoanBook is equal to the specified ReadOnlyLoanBook,
     * but when comparing Loans, only the editable fields will be checked for equality.
     */
    boolean hasEqualEditableFields(ReadOnlyLoanBook other);

}

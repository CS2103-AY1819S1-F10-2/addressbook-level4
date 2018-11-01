package loanbook.commons.events.ui;

import loanbook.model.loan.Loan;

/**
 * Represents a selection change in the Loan List Panel.
 */
public class LoanListPanelSelectionChangedEvent extends ListPanelSelectionChangedEvent<Loan> {

    public LoanListPanelSelectionChangedEvent(Loan newSelection) {
        super(newSelection);
    }

}

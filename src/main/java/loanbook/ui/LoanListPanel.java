package loanbook.ui;

import javafx.collections.ObservableList;
import loanbook.commons.events.ui.LoanListPanelSelectionChangedEvent;
import loanbook.model.loan.Loan;

/**
 * Panel containing the list of loans.
 */
public class LoanListPanel extends ListPanel<Loan> {

    public LoanListPanel(ObservableList<Loan> loanList) {
        super(LoanListPanel.class, loanList);
    }

    @Override
    protected void setSelectionChangeEvent(Loan oldValue, Loan newValue) {
        logger.fine("Selection in loan list panel changed to : '" + newValue + "'");
        raise(new LoanListPanelSelectionChangedEvent(newValue));
    }

    @Override
    protected LoanCard getNewCard(Loan loan, int displayedIndex) {
        return new LoanCard(loan, displayedIndex);
    }

}

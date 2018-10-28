package loanbook.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import loanbook.commons.core.LogsCenter;
import loanbook.commons.events.ui.LoanPanelSelectionChangedEvent;
import loanbook.model.loan.Loan;

/**
 * Panel containing the list of loans.
 */
public class LoanListPanel extends ListPanel<Loan> {

    private final Logger logger = LogsCenter.getLogger(LoanListPanel.class);

    public LoanListPanel(ObservableList<Loan> loanList) {
        super(loanList);
    }

    @Override
    protected void setSelectionChangeEvent(Loan oldValue, Loan newValue) {
        logger.fine("Selection in loan list panel changed to : '" + newValue + "'");
        raise(new LoanPanelSelectionChangedEvent(newValue));
    }

    @Override
    protected void logInfoMessage(String message) {
        logger.info(message);
    }

    @Override
    protected LoanCard getNewCard(Loan loan, int displayedIndex) {
        return new LoanCard(loan, displayedIndex);
    }

}

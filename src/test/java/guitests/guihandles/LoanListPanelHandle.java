package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import loanbook.model.loan.Loan;

/**
 * Provides a handle for {@code LoanListPanel} containing the list of {@code LoanCard}.
 */
public class LoanListPanelHandle extends ListPanelHandle<Loan, LoanCardHandle> {

    public LoanListPanelHandle(ListView<Loan> loanListPanelNode) {
        super(loanListPanelNode);
    }

    protected String getItemClassName() {
        return Loan.class.getSimpleName();
    }

    protected LoanCardHandle getNewCardHandle(Node cardNode) {
        return new LoanCardHandle(cardNode);
    }

}

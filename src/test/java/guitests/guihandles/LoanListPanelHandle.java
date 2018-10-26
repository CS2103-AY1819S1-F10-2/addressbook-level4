package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import loanbook.model.loan.Loan;

/**
 * Provides a handle for {@code LoanListPanel} containing the list of {@code LoanCard}.
 */
public class LoanListPanelHandle extends NodeHandle<ListView<Loan>> {
    public static final String LOAN_LIST_VIEW_ID = "#loanListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Loan> lastRememberedSelectedLoanCard;

    public LoanListPanelHandle(ListView<Loan> loanListPanelNode) {
        super(loanListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code LoanCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public LoanCardHandle getHandleToSelectedCard() {
        List<Loan> selectedLoanList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedLoanList.size() != 1) {
            throw new AssertionError("Loan list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(LoanCardHandle::new)
                .filter(handle -> handle.equals(selectedLoanList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Loan> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code loan}.
     */
    public void navigateToCard(Loan loan) {
        if (!getRootNode().getItems().contains(loan)) {
            throw new IllegalArgumentException("Loan does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(loan);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code LoanCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the loan card handle of a loan associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public LoanCardHandle getLoanCardHandle(int index) {
        Object[] s = getAllCardNodes().stream().map(LoanCardHandle::new).toArray();
        return getAllCardNodes().stream()
                .map(LoanCardHandle::new)
                .filter(handle -> handle.equals(getLoan(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Loan getLoan(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code LoanCard} in the list.
     */
    public void rememberSelectedLoanCard() {
        List<Loan> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedLoanCard = Optional.empty();
        } else {
            lastRememberedSelectedLoanCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code LoanCard} is different from the value remembered by the most recent
     * {@code rememberSelectedLoanCard()} call.
     */
    public boolean isSelectedLoanCardChanged() {
        List<Loan> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedLoanCard.isPresent();
        } else {
            return !lastRememberedSelectedLoanCard.isPresent()
                    || !lastRememberedSelectedLoanCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

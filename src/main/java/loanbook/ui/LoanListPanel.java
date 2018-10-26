package loanbook.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import loanbook.commons.core.LogsCenter;
import loanbook.commons.events.ui.JumpToListRequestEvent;
import loanbook.commons.events.ui.LoanPanelSelectionChangedEvent;
import loanbook.model.loan.Loan;

/**
 * Panel containing the list of loans.
 */
public class LoanListPanel extends UiPart<Region> {
    private static final String FXML = "LoanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoanListPanel.class);

    @FXML
    private ListView<Loan> loanListView;

    public LoanListPanel(ObservableList<Loan> loanList) {
        super(FXML);
        setConnections(loanList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Loan> loanList) {
        loanListView.setItems(loanList);
        loanListView.setCellFactory(listView -> new LoanListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        loanListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in loan list panel changed to : '" + newValue + "'");
                        raise(new LoanPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code LoanCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            loanListView.scrollTo(index);
            loanListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Loan} using a {@code LoanCard}.
     */
    class LoanListViewCell extends ListCell<Loan> {
        @Override
        protected void updateItem(Loan loan, boolean empty) {
            super.updateItem(loan, empty);

            if (empty || loan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LoanCard(loan, getIndex() + 1).getRoot());
            }
        }
    }

}

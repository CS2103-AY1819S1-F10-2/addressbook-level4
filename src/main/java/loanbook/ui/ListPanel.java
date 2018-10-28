package loanbook.ui;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import loanbook.commons.core.LogsCenter;
import loanbook.commons.events.ui.JumpToListRequestEvent;

/**
 * Panel containing a list of items.
 */
public abstract class ListPanel<T> extends UiPart<Region> {
    /*
     * Since all list panels occupy the same space and have the same formatting, They all share one FXML file.
     */
    private static final String FXML = "ListPanel.fxml";

    @FXML
    protected ListView<T> listView;

    public ListPanel(ObservableList<T> list) {
        super(FXML);
        setConnections(list);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<T> list) {
        listView.setItems(list);
        listView.setCellFactory(listView -> new ListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    protected abstract void setSelectionChangeEvent();

    private void setEventHandlerForSelectionChangeEvent() {
        listView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setSelectionChangeEvent();
                }
            });
    }

    /**
     * Scrolls to the {@code LoanCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            listView.scrollTo(index);
            listView.getSelectionModel().clearAndSelect(index);
        });
    }

    protected abstract void logInfoMessage(String message);

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logInfoMessage(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    protected abstract ListCard<T> getNewCard(T item, int displayedIndex);

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Loan} using a {@code LoanCard}.
     */
    class ListViewCell extends ListCell<T> {
        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
                setText(null);
            } else {
                ListCard<T> card = getNewCard(item, getIndex() + 1);
                setGraphic(card.getRoot());
            }
        }
    }

}

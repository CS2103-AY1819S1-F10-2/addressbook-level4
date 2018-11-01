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

/**
 * Panel containing a list of items.
 */
public abstract class ListPanel<T> extends UiPart<Region> {
    /*
     * Since all list panels occupy the same space and have the same formatting, They all share one FXML file.
     */
    private static final String FXML = "ListPanel.fxml";

    protected final Logger logger;

    @FXML
    protected ListView<T> listView;

    public ListPanel(Class thisClass, ObservableList<T> list) {
        super(FXML);
        logger = LogsCenter.getLogger(thisClass);
        setConnections(list);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<T> list) {
        listView.setItems(list);
        listView.setCellFactory(listView -> new ListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    protected abstract void setSelectionChangeEvent(T oldValue, T newValue);

    private void setEventHandlerForSelectionChangeEvent() {
        listView.getSelectionModel().selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    setSelectionChangeEvent(oldValue, newValue);
                }
            });
    }

    /**
     * Scrolls to the {@code ListCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            listView.scrollTo(index);
            listView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    protected abstract ListCard<T> getNewCard(T item, int displayedIndex);

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code T} object using a {@code ListCard<T>}.
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

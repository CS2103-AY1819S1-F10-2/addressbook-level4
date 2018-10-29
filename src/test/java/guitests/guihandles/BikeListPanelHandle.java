package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import loanbook.model.bike.Bike;

/**
 * Provides a handle for {@code BikeListPanel} containing the list of {@code BikeCard}.
 */
public class BikeListPanelHandle extends NodeHandle<ListView<Bike>> {
    public static final String BIKE_LIST_VIEW_ID = "#listView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Bike> lastRememberedSelectedBikeCard;

    public BikeListPanelHandle(ListView<Bike> bikeListPanelNode) {
        super(bikeListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code BikeCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BikeCardHandle getHandleToSelectedCard() {
        List<Bike> selectedBikeList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedBikeList.size() != 1) {
            throw new AssertionError("Bike list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(BikeCardHandle::new)
            .filter(handle -> handle.equals(selectedBikeList.get(0)))
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
        List<Bike> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code bike}.
     */
    public void navigateToCard(Bike bike) {
        if (!getRootNode().getItems().contains(bike)) {
            throw new IllegalArgumentException("Bike does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(bike);
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
     * Selects the {@code BikeCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the bike card handle of a bike associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public BikeCardHandle getBikeCardHandle(int index) {
        Object[] s = getAllCardNodes().stream().map(BikeCardHandle::new).toArray();
        return getAllCardNodes().stream()
            .map(BikeCardHandle::new)
            .filter(handle -> handle.equals(getBike(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private Bike getBike(int index) {
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
     * Remembers the selected {@code BikeCard} in the list.
     */
    public void rememberSelectedBikeCard() {
        List<Bike> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedBikeCard = Optional.empty();
        } else {
            lastRememberedSelectedBikeCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code BikeCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBikeCard()} call.
     */
    public boolean isSelectedBikeCardChanged() {
        List<Bike> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedBikeCard.isPresent();
        } else {
            return !lastRememberedSelectedBikeCard.isPresent()
                || !lastRememberedSelectedBikeCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

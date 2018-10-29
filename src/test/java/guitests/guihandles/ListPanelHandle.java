package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;

/**
 * Provides a handle for a {@code ListPanel<T>} containing a list of {@code ListCard<T>}.
 */
public abstract class ListPanelHandle<T, CardHandle extends ListCardHandle<T>> extends NodeHandle<ListView<T>> {

    public static final String LIST_VIEW_ID = "#listView";
    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<T> lastRememberedSelectedCard;

    public ListPanelHandle(ListView<T> listPanelNode) {
        super(listPanelNode);
    }

    /**
     * Gets the class name of T.
     * An internal function to help with error reporting.
     * @return The class name of T.
     */
    protected abstract String getItemClassName();

    /**
     * Creates a new CardHandle object using the provided cardNode.
     * @param cardNode A node for a ListCard.
     * @return A new CardHandle.
     */
    protected abstract CardHandle getNewCardHandle(Node cardNode);

    /**
     * Returns a handle to the selected {@code CardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CardHandle getHandleToSelectedCard() {
        List<T> selectedList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedList.size() != 1) {
            throw new AssertionError(getItemClassName() + " list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(this::getNewCardHandle)
            .filter(handle -> handle.contains(selectedList.get(0)))
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
        List<T> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError(getItemClassName() + " card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code item}.
     */
    public void navigateToCard(T item) {
        if (!getRootNode().getItems().contains(item)) {
            throw new IllegalArgumentException(getItemClassName() + " does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(item);
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
     * Selects the {@code ListCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the card handle of an item associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public CardHandle getCardHandle(int index) {
        Object[] s = getAllCardNodes().stream().map(this::getNewCardHandle).toArray();
        return getAllCardNodes().stream()
            .map(this::getNewCardHandle)
            .filter(handle -> handle.contains(getItem(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private T getItem(int index) {
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
    public void rememberSelectedCard() {
        List<T> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedCard = Optional.empty();
        } else {
            lastRememberedSelectedCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code ListCard} is different from the value remembered by the most recent
     * {@code rememberSelectedBikeCard()} call.
     */
    public boolean isSelectedCardChanged() {
        List<T> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedCard.isPresent();
        } else {
            return !lastRememberedSelectedCard.isPresent()
                || !lastRememberedSelectedCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}

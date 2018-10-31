package loanbook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * A UI component that displays information of an object of type {@code T}.
 */
public abstract class ListCard<T> extends UiPart<Region> {

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final T item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;


    public ListCard(String fxmlFile, T item, int displayedIndex) {
        super(fxmlFile);
        this.item = item;
        id.setText(Integer.toString(displayedIndex));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }
        if (other == null) {
            return false; // null objects are not equal to this object (which is non-null)
        }
        if (other.getClass() != this.getClass()) {
            return false; // These objects are not of the same type
        }

        // returns true iff both ListCards have the same ID and refer to the same object
        ListCard card = (ListCard) other;
        return id.getText().equals(card.id.getText())
            && item.equals(card.item);
    }
}


package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a {@code ListCard<T>} in the list panel.
 */
public class ListCardHandle extends NodeHandle<Node> {

    private static final String ID_FIELD_ID = "#id";

    private final Label idLabel;

    public ListCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }
}

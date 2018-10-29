package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import loanbook.model.bike.Bike;

/**
 * Provides a handle to a bike card in the bike list panel.
 */
public class BikeCardHandle extends ListCardHandle<Bike> {

    private static final String NAME_FIELD_ID = "#name";

    private final Label nameLabel;

    public BikeCardHandle(Node cardNode) {
        super(cardNode);

        nameLabel = getChildNode(NAME_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code bike}.
     */
    public boolean contains(Bike bike) {
        return getName().equals(bike.getName().value);
    }
}

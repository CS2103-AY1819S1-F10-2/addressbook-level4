package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.ListView;

import loanbook.model.bike.Bike;

/**
 * Provides a handle for {@code BikeListPanel} containing the list of {@code BikeCard}.
 */
public class BikeListPanelHandle extends ListPanelHandle<Bike, BikeCardHandle> {

    public BikeListPanelHandle(ListView<Bike> bikeListPanelNode) {
        super(bikeListPanelNode);
    }

    protected String getItemClassName() {
        return Bike.class.getSimpleName();
    }

    protected String getCardPaneCssId() {
        return "#bikeCardPane";
    }

    protected BikeCardHandle getNewCardHandle(Node cardNode) {
        return new BikeCardHandle(cardNode);
    }

}

package loanbook.ui;

import javafx.collections.ObservableList;
import loanbook.commons.events.ui.BikeListPanelSelectionChangedEvent;
import loanbook.model.bike.Bike;

/**
 * Panel containing the list of bikes.
 */
public class BikeListPanel extends ListPanel<Bike> {

    public BikeListPanel(ObservableList<Bike> bikeList) {
        super(BikeListPanel.class, bikeList);
    }

    @Override
    protected void setSelectionChangeEvent(Bike oldValue, Bike newValue) {
        logger.fine("Selection in bike list panel changed to : '" + newValue + "'");
        raise(new BikeListPanelSelectionChangedEvent(newValue));
    }

    @Override
    protected BikeCard getNewCard(Bike bike, int displayedIndex) {
        return new BikeCard(bike, displayedIndex);
    }
}

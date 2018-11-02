package loanbook.commons.events.ui;

import loanbook.model.bike.Bike;

/**
 * Represents a selection change in the Bike List Panel.
 */
public class BikeListPanelSelectionChangedEvent extends ListPanelSelectionChangedEvent<Bike> {

    public BikeListPanelSelectionChangedEvent(Bike newSelection) {
        super(newSelection);
    }

}

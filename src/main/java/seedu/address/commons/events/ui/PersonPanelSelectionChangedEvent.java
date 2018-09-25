package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.loan.Loan;

/**
 * Represents a selection change in the Loan List Panel
 */
public class PersonPanelSelectionChangedEvent extends BaseEvent {


    private final Loan newSelection;

    public PersonPanelSelectionChangedEvent(Loan newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Loan getNewSelection() {
        return newSelection;
    }
}

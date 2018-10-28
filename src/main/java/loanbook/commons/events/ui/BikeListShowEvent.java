package loanbook.commons.events.ui;

import loanbook.commons.events.BaseEvent;

/**
 * Represents a request to change the List Panel to view bikes.
 */
public class BikeListShowEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

package loanbook.commons.events.ui;

import loanbook.commons.events.BaseEvent;

/**
 * Represents a selection change in the List Panel.
 */
public class ListPanelSelectionChangedEvent<T> extends BaseEvent {

    private final T newSelection;

    public ListPanelSelectionChangedEvent(T newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public T getNewSelection() {
        return newSelection;
    }
}

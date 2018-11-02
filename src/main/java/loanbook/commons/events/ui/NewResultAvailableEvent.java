package loanbook.commons.events.ui;

import loanbook.commons.events.BaseEvent;

/**
 * Indicates that a new result is available.
 */
public class NewResultAvailableEvent extends BaseEvent {

    public final String message;

    public NewResultAvailableEvent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}

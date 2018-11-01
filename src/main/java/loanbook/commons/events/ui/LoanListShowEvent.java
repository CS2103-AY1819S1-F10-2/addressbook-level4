package loanbook.commons.events.ui;

import loanbook.commons.events.BaseEvent;

/**
 * Represents a request to change the List Panel to view loans.
 */
public class LoanListShowEvent extends BaseEvent {

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}

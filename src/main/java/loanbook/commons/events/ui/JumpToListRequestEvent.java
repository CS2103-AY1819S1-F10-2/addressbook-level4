package loanbook.commons.events.ui;

import loanbook.commons.core.index.Index;
import loanbook.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of loans
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}

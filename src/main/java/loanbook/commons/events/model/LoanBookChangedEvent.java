package loanbook.commons.events.model;

import loanbook.commons.events.BaseEvent;
import loanbook.model.ReadOnlyLoanBook;

/** Indicates the LoanBook in the model has changed*/
public class LoanBookChangedEvent extends BaseEvent {

    public final ReadOnlyLoanBook data;

    public LoanBookChangedEvent(ReadOnlyLoanBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of loans " + data.getLoanList().size();
    }
}

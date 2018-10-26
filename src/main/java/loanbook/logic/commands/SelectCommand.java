package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import loanbook.commons.core.EventsCenter;
import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.commons.events.ui.JumpToListRequestEvent;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Loan;

/**
 * Selects a loan identified using it's displayed index from the loan book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the loan identified by the index number used in the displayed loan list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_LOAN_SUCCESS = "Selected Loan: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Loan> filteredLoanList = model.getFilteredLoanList();

        if (targetIndex.getZeroBased() >= filteredLoanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_LOAN_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;

/**
 * Lists all loans in the loan book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all loans";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

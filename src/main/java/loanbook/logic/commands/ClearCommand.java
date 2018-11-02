package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;

/**
 * Clears the loans and resets the loan ID in the loan book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Loan book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.resetLoans();
        model.commitLoanBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.Password;

/**
 * Deletes a loan identified using it's displayed index from the loan book.
 */
public class ResetAllCommand extends Command {

    public static final String COMMAND_WORD = "resetall";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resets the entire Loan book. This command clears all Loans, Bikes and resets the Loan ID. "
            + "Requires a password for verification.\n"
            + "Parameters: " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " x/a12345";

    public static final String MESSAGE_RESET_SUCCESS = "Loan book has been successfully reset!";

    private final Password targetPassword;

    public ResetAllCommand(Password pass) {
        targetPassword = pass;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!Password.isSamePassword(model.getPass(), targetPassword)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
        }

        model.resetLoans();
        model.resetBikes();
        model.resetId();
        model.commitLoanBook();
        return new CommandResult(MESSAGE_RESET_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResetAllCommand // instanceof handles nulls
                && targetPassword.equals(((ResetAllCommand) other).targetPassword)); // state check
    }
}

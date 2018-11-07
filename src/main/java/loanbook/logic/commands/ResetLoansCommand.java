package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.Password;

/**
 * Clears the loans and bikes and resets the loan ID in the loan book.
 */
public class ResetLoansCommand extends PasswordProtectedCommand {

    public static final String COMMAND_WORD = "resetloans";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears the loans from the Loan book and resets the Loan ID. "
            + "Requires a password for verification.\n"
            + "Parameters: " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " x/a12345";

    public static final String MESSAGE_RESET_LOANS_SUCCESS = "Loan book has been successfully reset!";

    public ResetLoansCommand(Password pass) {
        super(pass);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        assertCorrectPassword(model);

        model.resetLoans();
        model.resetId();
        model.commitLoanBook();
        return new CommandResult(MESSAGE_RESET_LOANS_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ResetLoansCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_INDEX;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Loan;

/**
 * Deletes a loan identified using it's displayed index from the loan book.
 */
public class DeleteCommand extends PasswordProtectedCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the loan identified by the index number used in the displayed loan list.\n"
            + "Requires a password for verification.\n"
            + "Parameters: " + PREFIX_INDEX + "INDEX (must be a positive integer) " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " i/1 x/a12345";

    public static final String MESSAGE_DELETE_LOAN_SUCCESS = "Deleted Loan: %1$s";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex, String pass) {
        super(pass, COMMAND_WORD);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        assertCorrectPassword(model);

        List<Loan> lastShownList = model.getFilteredLoanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
        }

        Loan loanToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteLoan(loanToDelete);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && super.equals(other)
                && targetIndex.equals(((DeleteCommand) other).targetIndex));
    }
}

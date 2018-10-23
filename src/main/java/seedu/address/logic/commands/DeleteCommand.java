package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.loan.Loan;

/**
 * Deletes a loan identified using it's displayed index from the loan book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the loan identified by the index number used in the displayed loan list.\n"
            + "Requires a password for verification.\n"
            + "Parameters: " + PREFIX_INDEX + "INDEX (must be a positive integer) " + PREFIX_PASSWORD + "PASSWORD\n"
            + "Example: " + COMMAND_WORD + " i/1 x/a12345";

    public static final String MESSAGE_DELETE_LOAN_SUCCESS = "Deleted Loan: %1$s";

    private final Index targetIndex;
    private final Password targetPassword;

    public DeleteCommand(Index targetIndex, Password pass) {
        this.targetIndex = targetIndex;
        targetPassword = pass;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        requireNonNull(model);
        List<Loan> lastShownList = model.getFilteredLoanList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
        }

        if (!Password.isSamePassword(model.getPass(), targetPassword)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
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
                && targetIndex.equals(((DeleteCommand) other).targetIndex)
                && targetPassword.equals(((DeleteCommand) other).targetPassword)); // state check
    }
}

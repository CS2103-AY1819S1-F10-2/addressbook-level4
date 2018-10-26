package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;

import seedu.address.logic.CommandHistory;
import seedu.address.model.LoanBook;
import seedu.address.model.Model;

/**
 * Clears the loans in the loan book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Loan book has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setLoans(Collections.emptyList());
        model.commitLoanBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

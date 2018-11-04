package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.loan.NameContainsKeywordsPredicate;

/**
 * Finds and lists all loans in loan book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all loans whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLoanList(predicate.forLoans());
        return new CommandResult(
                String.format(Messages.MESSAGE_LOANS_LISTED_OVERVIEW, model.getFilteredLoanList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

import java.util.List;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.loan.Loan;

/**
 * Terminates the program.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_SUMMARY_ACKNOWLEDGEMENT = "Summarized LoanBook!\n%1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        Summary summary = new Summary();

        List<Loan> lastShownList = model.getFilteredLoanList();

        for (Loan loan : lastShownList) {
            summary.addLoan(loan);
        }

        return new CommandResult(String.format(MESSAGE_SUMMARY_ACKNOWLEDGEMENT, summary.getSummary()));
    }
}

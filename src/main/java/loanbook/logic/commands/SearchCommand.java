package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import loanbook.commons.core.EventsCenter;
import loanbook.commons.events.ui.LoanListShowEvent;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanTime;

/**
 * Finds and lists all loans in loan book whose loan start time is within the provided range.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Search for loans between STARTDATE and ENDDATE.\n"
            + "Parameters: STARTDATE ENDDATE\n"
            + "Example: " + COMMAND_WORD + " 2018-01-01 2018-12-31";

    public static final String MESSAGE_SUCCESS = "Showing all loans that was created between: %s and %s.";
    public static final String MESSAGE_FAILURE = "There are no loans that was created between: %s and %s.";

    private final LoanTime startDate;
    private final LoanTime endDate;

    /**
     * Creates an SearchCommand to check loans that was created between {@code startDate} and {@code endDate}.
     */
    public SearchCommand(LoanTime startDate, LoanTime endDate) {
        // TODO ALLOW FOR NULL STARTDATE AND ENDDATE.
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        model.updateFilteredLoanList(loan -> loan.getLoanStartTime().isBetweenRange(startDate, endDate));

        List<Loan> filteredLoanList = model.getFilteredLoanList();

        if (filteredLoanList.size() == 0) {
            throw new CommandException(String.format(MESSAGE_FAILURE, startDate, endDate));
        }

        EventsCenter.getInstance().post(new LoanListShowEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, startDate, endDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchCommand // instanceof handles nulls
                && startDate.equals(((SearchCommand) other).startDate)
                && endDate.equals(((SearchCommand) other).endDate));
    }
}

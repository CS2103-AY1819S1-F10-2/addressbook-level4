package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.LoanTime;

/**
 * Adds a loan to the loan book.
 */
public class ReturnCommand extends Command {

    public static final String COMMAND_WORD = "return";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Returns a loan within the loanbook. "
            + "Parameters: "
            + PREFIX_INDEX + "LOAN_INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Loan Returned: %1$s\nThe customer should pay $%2$.2f.";
    public static final String MESSAGE_LOAN_NOT_ONGOING = "This loan is not ongoing";

    private final Index index;

    /**
     * Creates an AddCommand to add the specified {@code Loan}
     */
    public ReturnCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Loan> lastShownList = model.getFilteredLoanList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
        }

        Loan loanToReturn = lastShownList.get(index.getZeroBased());

        if (loanToReturn.getLoanStatus() != LoanStatus.ONGOING) {
            throw new CommandException(MESSAGE_LOAN_NOT_ONGOING);
        }

        Loan editedLoan = createReturnedLoan(loanToReturn);
        double cost = editedLoan.calculateCost();

        model.updateLoan(loanToReturn, editedLoan);
        model.commitLoanBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedLoan, cost));
    }

    /**
     * Creates a returned loan from an ongoing loan {@code loanToReturn}.
     */
    private static Loan createReturnedLoan(Loan loanToReturn) {
        return new Loan(
                loanToReturn.getLoanId(),
                loanToReturn.getName(),
                loanToReturn.getNric(),
                loanToReturn.getPhone(),
                loanToReturn.getEmail(),
                loanToReturn.getBike(),
                loanToReturn.getLoanRate(),
                loanToReturn.getLoanStartTime(),
                new LoanTime(),
                LoanStatus.RETURNED,
                loanToReturn.getTags()
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReturnCommand // instanceof handles nulls
                && index.equals(((ReturnCommand) other).index));
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

import java.util.List;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;

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

/**
 * Class to encapsulate all the statistics to be kept track of.
 */
class Summary {
    public static final String MESSAGE_SUMMARY = "You have loaned %1$d loan(s). "
            + "You have %2$d loan(s) ongoing.\n"
            + "Your total revenue is $%3$.2f.";

    private int numLoans;
    private int numLoansInProgress;
    private double totalRevenue;

    public Summary() {
        numLoans = 0;
        numLoansInProgress = 0;
        totalRevenue = 0;
    }

    /**
     * Adds the statistics of a loan into the summary object
     * @param loan
     */
    public void addLoan(Loan loan) {
        addNumLoans();
        if (loan.getLoanStatus() == LoanStatus.ONGOING) {
            addNumLoansInProgress();
        } else {
            addTotalRevenue(Math.floor(loan.calculateCost() * 100) / 100);
        }
    }

    public int getNumLoans() {
        return numLoans;
    }

    public void addNumLoans() {
        numLoans++;
    }

    public int getNumLoansInProgress() {
        return numLoansInProgress;
    }

    public void addNumLoansInProgress() {
        numLoansInProgress++;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void addTotalRevenue(double revenue) {
        totalRevenue += revenue;
    }

    public String getSummary() {
        return String.format(MESSAGE_SUMMARY,
                getNumLoans(),
                getNumLoansInProgress(),
                getTotalRevenue());
    }
}

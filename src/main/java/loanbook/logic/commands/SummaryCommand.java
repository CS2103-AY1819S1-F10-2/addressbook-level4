package loanbook.logic.commands;

import loanbook.commons.core.EventsCenter;
import loanbook.commons.events.ui.ExitAppRequestEvent;
import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;

/**
 * Terminates the program.
 */
public class SummaryCommand extends Command {

    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_SUMMARY_ACKNOWLEDGEMENT = "Summarized LoanBook!\n" +
            "You have loaned %1$d loan(s). There are %2$d loan(s) ongoing.\n" +
            "Your total revenue is $%3$.2f.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        Summary summary = new Summary();

        List<Loan> lastShownList = model.getFilteredLoanList();

        for (Loan loan : lastShownList){
            summary.addLoan(loan);
        }

        return new CommandResult(String.format(MESSAGE_SUMMARY_ACKNOWLEDGEMENT,
                summary.getNumLoans(),
                summary.getNumLoansInProgress(),
                summary.getTotalRevenue()));
    }
}

class Summary {
    int numLoans;
    int numLoansInProgress;
    double totalRevenue;

    public Summary(){
        numLoans = 0;
        numLoansInProgress = 0;
        totalRevenue = 0;
    }

    public void addLoan(Loan loan){
        numLoans++;
        if (loan.getLoanStatus() == LoanStatus.ONGOING){
            numLoansInProgress++;
        }
        else {
            totalRevenue += Math.floor(loan.calculateCost()*100)/100;
        }
    }

    public int getNumLoans(){
        return numLoans;
    }

    public int getNumLoansInProgress(){
        return numLoansInProgress;
    }

    public double getTotalRevenue(){
        return totalRevenue;
    }
}
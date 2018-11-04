package loanbook.logic.commands;

import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanStatus;

/**
 * Class to encapsulate all the statistics to be kept track of.
 */
public class Summary {
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
     * Adds the statistics of a loan into the summary object.
     */
    public void addLoan(Loan loan) {
        incrementNumLoans();
        if (loan.getLoanStatus() == LoanStatus.ONGOING) {
            addNumLoansInProgress();
        } else {
            addTotalRevenue(Math.floor(loan.calculateCost() * 100) / 100);
        }
    }

    public int getNumLoans() {
        return numLoans;
    }

    private void incrementNumLoans() {
        numLoans++;
    }

    public int getNumLoansInProgress() {
        return numLoansInProgress;
    }

    private void addNumLoansInProgress() {
        numLoansInProgress++;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    private void addTotalRevenue(double revenue) {
        totalRevenue += revenue;
    }

    public String getSummary() {
        return String.format(MESSAGE_SUMMARY,
                getNumLoans(),
                getNumLoansInProgress(),
                getTotalRevenue());
    }
}

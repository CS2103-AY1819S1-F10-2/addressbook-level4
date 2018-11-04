package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static loanbook.testutil.TypicalLoans.ALICE;
import static loanbook.testutil.TypicalLoans.BOB;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.LoanTime;


public class SummaryCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void addLoan_summaryObject_success() {
        Summary summary = new Summary();

        // Add a finished loan
        summary.addLoan(new Loan(ALICE.getLoanId(),
                ALICE.getName(),
                ALICE.getNric(),
                ALICE.getPhone(),
                ALICE.getEmail(),
                ALICE.getBike(),
                new LoanRate("6.00"),
                new LoanTime("2001-02-03 19:06"),
                new LoanTime("2001-02-03 20:06"),
                LoanStatus.RETURNED,
                ALICE.getTags()));

        assertEquals(summary.getNumLoans(), 1);
        assertEquals(summary.getNumLoansInProgress(), 0);
        assertEquals(summary.getTotalRevenue(), 6.0, 0.005);

        summary.addLoan(new Loan(BOB.getLoanId(),
                BOB.getName(),
                BOB.getNric(),
                BOB.getPhone(),
                BOB.getEmail(),
                BOB.getBike(),
                new LoanRate("6.00"),
                BOB.getTags()));

        assertEquals(summary.getNumLoans(), 2);
        assertEquals(summary.getNumLoansInProgress(), 1);
        assertEquals(summary.getTotalRevenue(), 6.0, 0.005);
    }

    @Test
    public void execute_emptyLoanBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        // Empty loanbook should have 0 for all fields
        String expectedMessage = String.format(SummaryCommand.MESSAGE_SUMMARY_ACKNOWLEDGEMENT,
                new Summary().getSummary());

        assertCommandSuccess(new SummaryCommand(), model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nonEmptyLoanBook_success() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLoanBook(), new UserPrefs());

        Summary summary = new Summary();

        for (Loan loan : expectedModel.getLoanBook().getLoanList()) {
            summary.addLoan(loan);
        }

        String expectedMessage = String.format(SummaryCommand.MESSAGE_SUMMARY_ACKNOWLEDGEMENT, summary.getSummary());

        assertCommandSuccess(new SummaryCommand(), model, commandHistory, expectedMessage, expectedModel);
    }

}

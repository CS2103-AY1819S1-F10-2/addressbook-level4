package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.testutil.TypicalIndexes.INDEX_THIRD_LOAN;
import static loanbook.testutil.TypicalLoanBook.getLoanBookWithUnreturnedLoans;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.model.LoanBook;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanTime;
import loanbook.testutil.LoanBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class ReturnCommandTest {
    // Create a LoanBook with only unreturned loans
    private Model model = new ModelManager(getLoanBookWithUnreturnedLoans(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_returnOngoingLoan_success() {
        Loan newLoan = new LoanBuilder().build();
        Loan returnedLoan = new Loan(newLoan.getLoanId(),
                newLoan.getName(),
                newLoan.getNric(),
                newLoan.getPhone(),
                newLoan.getEmail(),
                newLoan.getBike(),
                newLoan.getLoanRate(),
                newLoan.getLoanStartTime(),
                new LoanTime(),
                newLoan.getTags());

        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_LOAN);

        String expectedMessage = String.format(ReturnCommand.MESSAGE_SUCCESS,
                returnedLoan, returnedLoan.calculateCost());

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateLoan(model.getFilteredLoanList().get(0), returnedLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(returnCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Edit filtered list where index is larger than size of loan book
     */
    @Test
    public void execute_returnInvalidLoanIndex_failure() {
        ReturnCommand returnCommand = new ReturnCommand(INDEX_SECOND_LOAN);

        assertCommandFailure(returnCommand, model, commandHistory, ReturnCommand.MESSAGE_LOAN_NOT_ONGOING);
    }

    @Test
    public void execute_returnAlreadyReturned_failure() {
        ReturnCommand returnCommand = new ReturnCommand(INDEX_THIRD_LOAN);

        assertCommandFailure(returnCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final ReturnCommand standardCommand = new ReturnCommand(INDEX_FIRST_LOAN);

        // same values -> returns true
        ReturnCommand commandWithSameValues = new ReturnCommand(INDEX_FIRST_LOAN);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new ReturnCommand(INDEX_SECOND_LOAN)));
    }

}

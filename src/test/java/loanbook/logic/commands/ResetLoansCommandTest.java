package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;

public class ResetLoansCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyLoanBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitLoanBook();

        String expectedPassword = "a12345";
        ResetLoansCommand command = new ResetLoansCommand(expectedPassword);

        assertCommandSuccess(command, model, commandHistory, ResetLoansCommand.MESSAGE_RESET_LOANS_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_nonEmptyLoanBook_success() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        expectedModel.resetLoans();
        expectedModel.resetId();
        expectedModel.commitLoanBook();

        String expectedPassword = "a12345";
        ResetLoansCommand command = new ResetLoansCommand(expectedPassword);

        assertCommandSuccess(command, model, commandHistory, ResetLoansCommand.MESSAGE_RESET_LOANS_SUCCESS,
                expectedModel);
    }

}

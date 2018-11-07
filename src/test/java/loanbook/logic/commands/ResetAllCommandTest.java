package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.Password;
import loanbook.model.UserPrefs;

public class ResetAllCommandTest {

    private static final Password CORRECT_PASSWORD = new Password("a12345");
    private static final Password INCORRECT_PASSWORD = new Password("incorrectPass");
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyLoanBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitLoanBook();

        assertCommandSuccess(new ResetAllCommand(CORRECT_PASSWORD), model, commandHistory,
                ResetAllCommand.MESSAGE_RESET_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLoanBook_success() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        expectedModel.resetLoans();
        expectedModel.resetBikes();
        expectedModel.resetId();
        expectedModel.commitLoanBook();

        assertCommandSuccess(new ResetAllCommand(CORRECT_PASSWORD), model, commandHistory,
                ResetAllCommand.MESSAGE_RESET_ALL_SUCCESS, expectedModel);
    }

    @Test
    public void execute_wrongPassword_commandExceptionThrown() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());

        assertCommandFailure(new ResetAllCommand(INCORRECT_PASSWORD), model, commandHistory,
                Messages.MESSAGE_INVALID_PASSWORD);
    }

    @Test
    public void equalityTest() {
        ResetAllCommand resetAllCommand1 = new ResetAllCommand(new Password("identical"));
        ResetAllCommand resetAllCommand2 = new ResetAllCommand(new Password("identical"));
        ResetAllCommand resetAllCommand3 = new ResetAllCommand(new Password("different"));

        assertEquals(resetAllCommand1, resetAllCommand1); // Same instance
        assertEquals(resetAllCommand1, resetAllCommand2); // Same password
        assertNotEquals(resetAllCommand1, resetAllCommand3); // Different password

        assertNotEquals(resetAllCommand1, new ListCommand()); // Different types
    }

}

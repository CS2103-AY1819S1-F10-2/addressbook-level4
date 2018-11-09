package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.PASSWORD1;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL4;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.Password;
import loanbook.model.UserPrefs;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanStatus;

public class RemindCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemindCommand(null, new LoanId("0"));
    }

    @Test
    public void constructor_nullId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemindCommand(PASSWORD1, null);
    }

    @Test
    public void execute_remindSuccessful() throws Exception {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDefaultEmail(VALID_USER_EMAIL4);
        Model modelStub = new ModelManager(getTypicalLoanBook(), userPrefs);
        LoanId id = new LoanId("0");
        CommandResult commandResult = new RemindCommand(PASSWORD1, id).execute(modelStub, commandHistory);

        assertEquals(RemindCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_noTargetLoan_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        LoanId id = new LoanId("999");
        RemindCommand command = new RemindCommand(PASSWORD1, id);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_LOAN);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sendToReturnedLoan_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        ReturnCommand returnCommand = new ReturnCommand(INDEX_FIRST_LOAN);
        returnCommand.execute(modelStub, commandHistory);

        LoanId id = new LoanId("0");
        RemindCommand command = new RemindCommand(PASSWORD1, id);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_LOAN_IS_DONE, LoanStatus.RETURNED.toString()));
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sendToDeletedLoan_throwsCommandException() throws Exception {
        Model modelStub = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password("a12345"));
        deleteCommand.execute(modelStub, commandHistory);

        LoanId id = new LoanId("0");
        RemindCommand command = new RemindCommand(PASSWORD1, id);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_LOAN);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_wrongEmailPassword_throwsCommandException() throws Exception {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setDefaultEmail(VALID_USER_EMAIL4);
        Model modelStub = new ModelManager(getTypicalLoanBook(), userPrefs);
        LoanId id = new LoanId("0");
        RemindCommand command = new RemindCommand(PASSWORD2, id);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_AUTHEN_FAILURE);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        final RemindCommand standardCommand = new RemindCommand(PASSWORD1, new LoanId("0"));
        RemindCommand commandWithSameValues = new RemindCommand(PASSWORD1, new LoanId("0"));

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different value -> returns false
        assertFalse(standardCommand.equals(new RemindCommand(PASSWORD2, new LoanId("0"))));
        assertFalse(standardCommand.equals(new RemindCommand(PASSWORD1, new LoanId("1"))));
    }
}

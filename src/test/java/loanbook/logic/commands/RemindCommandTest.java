package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.PASSWORD1;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL4;
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
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.bike.Bike;
import loanbook.model.loan.LoanStatus;
import loanbook.model.loan.Name;
import loanbook.testutil.ModelStub;
import loanbook.testutil.TypicalLoanBook;

public class RemindCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemindCommand(null, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE1)));
    }

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemindCommand(PASSWORD1, null, new Bike(new Name(VALID_NAME_BIKE1)));
    }

    @Test
    public void constructor_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RemindCommand(PASSWORD1, new Name(VALID_NAME_AMY), null);
    }

    @Test
    public void execute_remindSuccessful() throws Exception {
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();
        Name name = new Name("Alice Pauline");
        Bike bike = new Bike(new Name(VALID_NAME_BIKE1));
        CommandResult commandResult = new RemindCommand(PASSWORD1, name, bike).execute(modelStub, commandHistory);

        assertEquals(RemindCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_noTargetLoan_throwsCommandException() throws Exception {
        Name name = new Name("Alice Pauline");
        Bike bike = new Bike(new Name(VALID_NAME_BIKE2));
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();
        RemindCommand command = new RemindCommand(PASSWORD1, name, bike);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(Messages.MESSAGE_INVALID_INFO, name, bike.getName()));
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sendToReturnedLoan_throwsCommandException() throws Exception {
        ModelStubWithTwoLoans modelStub = new ModelStubWithTwoLoans();
        Name name = new Name("Alice Pauline");
        Bike bike = new Bike(new Name(VALID_NAME_BIKE1));
        RemindCommand command = new RemindCommand(PASSWORD1, name, bike);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(String.format(Messages.MESSAGE_LOAN_IS_DONE,
                LoanStatus.RETURNED.toString())));
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sendToDeletedLoan_throwsCommandException() throws Exception {
        ModelStubWithTwoLoans modelStub = new ModelStubWithTwoLoans();
        Name name = new Name("Benson Meier");
        Bike bike = new Bike(new Name(VALID_NAME_BIKE2));
        RemindCommand command = new RemindCommand(PASSWORD1, name, bike);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(String.format(Messages.MESSAGE_LOAN_IS_DONE,
                LoanStatus.DELETED.toString())));
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_wrongEmailPassword_throwsCommandException() throws Exception {
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();
        Name name = new Name("Alice Pauline");
        Bike bike = new Bike(new Name(VALID_NAME_BIKE1));
        RemindCommand command = new RemindCommand(PASSWORD2, name, bike);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_AUTHEN_FAILURE);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        final RemindCommand standardCommand =
                new RemindCommand(PASSWORD1, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE1)));
        RemindCommand commandWithSameValues =
                new RemindCommand(PASSWORD1, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE1)));

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different value -> returns false
        assertFalse(standardCommand.equals(
                new RemindCommand(PASSWORD2, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE1)))));
        assertFalse(standardCommand.equals(
                new RemindCommand(PASSWORD1, new Name(VALID_NAME_BOB), new Bike(new Name(VALID_NAME_BIKE1)))));
        assertFalse(standardCommand.equals(
                new RemindCommand(PASSWORD1, new Name(VALID_NAME_AMY), new Bike(new Name(VALID_NAME_BIKE2)))));
    }

    /**
     * A Model stub with a functional setMyEmail(), getMyEmail() and getLoanBook().
     */
    private class ModelStubWithUserEmail extends ModelStub {
        private String defaultEmail = VALID_USER_EMAIL4;
        private final ReadOnlyLoanBook loanBook = getTypicalLoanBook();

        @Override
        public void setMyEmail(String email) {
            defaultEmail = email;
        }

        @Override
        public String getMyEmail() {
            return defaultEmail;
        }

        @Override
        public ReadOnlyLoanBook getLoanBook() {
            return loanBook;
        }
    }

    /**
     * A Model stub with only one loan.
     */
    private class ModelStubWithTwoLoans extends ModelStub {
        private String defaultEmail = VALID_USER_EMAIL4;
        private final ReadOnlyLoanBook loanBook = TypicalLoanBook.getLoanBookForRemindTest();

        @Override
        public String getMyEmail() {
            return defaultEmail;
        }

        @Override
        public ReadOnlyLoanBook getLoanBook() {
            return loanBook;
        }
    }
}

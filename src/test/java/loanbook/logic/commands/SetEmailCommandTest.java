package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.PASSWORD1;
import static loanbook.logic.commands.CommandTestUtil.PASSWORD2;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Password;
import loanbook.model.loan.Email;
import loanbook.testutil.ModelStub;

public class SetEmailCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullNewEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetEmailCommand(null, new Password("a12345"));
    }

    @Test
    public void constructor_nullEmailPassword_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetEmailCommand(new Email(VALID_USER_EMAIL2), null);
    }

    @Test
    public void execute_setemailSuccessful() throws Exception {
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        CommandResult commandResult =
                new SetEmailCommand(
                        new Email(VALID_USER_EMAIL2), new Password(PASSWORD2)).execute(modelStub, commandHistory);

        assertEquals(SetEmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateNewEmail_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(new Email(VALID_USER_EMAIL1), new Password(PASSWORD2));
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SAME_AS_OLDEMAIL);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidNewEmail_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(
                new Email("invalid_example@gmail.com"), new Password(PASSWORD2));
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_EMAIL);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_wrongAppPassword_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(new Email(VALID_USER_EMAIL2), new Password(PASSWORD1));
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Email email1 = new Email(VALID_USER_EMAIL1);
        Email email2 = new Email(VALID_USER_EMAIL2);
        Password password = new Password("a12345");
        final SetEmailCommand standardCommand = new SetEmailCommand(email1, password);
        SetEmailCommand commandWithSameValues = new SetEmailCommand(email1, password);

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new UndoCommand()));

        // different value -> returns false
        assertFalse(standardCommand.equals(new SetEmailCommand(email2, password)));
    }

    /**
     * A Model stub with a functional setMyEmail() and getMyEmail().
     */
    private class ModelStubWithUserEmail extends ModelStub {
        private String defaultEmail = VALID_USER_EMAIL1;
        private String password = (new Password("a12345")).hashedPassword();

        @Override
        public void setMyEmail(String email) {
            defaultEmail = email;
        }

        @Override
        public String getMyEmail() {
            return defaultEmail;
        }

        @Override
        public String getPass() {
            return password;
        }
    }
}

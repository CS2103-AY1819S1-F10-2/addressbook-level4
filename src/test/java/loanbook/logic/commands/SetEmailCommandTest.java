package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.DEFAULT_USER_EMAIL;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL2;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL3;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL4;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.testutil.ModelStub;

public class SetEmailCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullOldEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetEmailCommand(null, VALID_USER_EMAIL1);
    }

    @Test
    public void constructor_nullNewEmail_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetEmailCommand(VALID_USER_EMAIL2, null);
    }

    @Test
    public void execute_setemailSuccessful() throws Exception {
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        // User's Email is default
        CommandResult commandResult =
                new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL1).execute(modelStub, commandHistory);

        assertEquals(SetEmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);

        // Change user's email from default to a valid gmail
        modelStub.setMyEmail(VALID_USER_EMAIL4);

        commandResult = new SetEmailCommand(VALID_USER_EMAIL4, VALID_USER_EMAIL1).execute(modelStub, commandHistory);

        assertEquals(SetEmailCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_wrongOldEmailWhenUserEmailIsDefault_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(VALID_USER_EMAIL1, VALID_USER_EMAIL2);
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_WRONG_OLDEMAIL);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_wrongOldEmail_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(VALID_USER_EMAIL1, VALID_USER_EMAIL2);
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_WRONG_OLDEMAIL);
        modelStub.setMyEmail(VALID_USER_EMAIL4);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_duplicateNewEmail_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(VALID_USER_EMAIL1, VALID_USER_EMAIL1);
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_DUPLICATE_FAILURE);
        modelStub.setMyEmail(VALID_USER_EMAIL1);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_invalidNewEmail_throwsCommandException() throws Exception {
        SetEmailCommand command = new SetEmailCommand(VALID_USER_EMAIL1, "example@gmail.com@gmail.com");
        ModelStubWithUserEmail modelStub = new ModelStubWithUserEmail();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_EMAIL);
        modelStub.setMyEmail(VALID_USER_EMAIL1);
        command.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        final SetEmailCommand standardCommand = new SetEmailCommand(VALID_USER_EMAIL1, VALID_USER_EMAIL2);
        final SetEmailCommand standardCommandWithDefault = new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL3);
        SetEmailCommand commandWithSameValues = new SetEmailCommand(VALID_USER_EMAIL1, VALID_USER_EMAIL2);
        SetEmailCommand commandWithSameValuesWithDefault = new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL3);

        // same values -> returns true
        assertTrue(standardCommand.equals(commandWithSameValues));
        assertTrue(standardCommandWithDefault.equals(commandWithSameValuesWithDefault));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(standardCommandWithDefault.equals(commandWithSameValuesWithDefault));

        // null -> returns false
        assertFalse(standardCommand == null);
        assertFalse(standardCommandWithDefault == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        assertFalse(standardCommandWithDefault.equals(new UndoCommand()));

        // different value -> returns false
        assertFalse(standardCommand.equals(new SetEmailCommand(VALID_USER_EMAIL4, VALID_USER_EMAIL2)));
        assertFalse(standardCommand.equals(new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL2)));
        assertFalse(standardCommandWithDefault.equals(new SetEmailCommand(DEFAULT_USER_EMAIL, VALID_USER_EMAIL4)));
    }

    /**
     * A Model stub with a functional setMyEmail() and getMyEmail().
     */
    private class ModelStubWithUserEmail extends ModelStub {
        private String defaultEmail = "default";

        @Override
        public void setMyEmail(String email) {
            defaultEmail = email;
        }

        @Override
        public String getMyEmail() {
            return defaultEmail;
        }
    }
}

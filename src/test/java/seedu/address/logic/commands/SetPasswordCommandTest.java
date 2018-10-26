package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Password;
import seedu.address.model.loan.LoanId;
import seedu.address.testutil.ModelStub;

public class SetPasswordCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullOldPass_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetPasswordCommand(null, new Password("123"));
    }

    @Test
    public void constructor_nullNewPass_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetPasswordCommand(new Password("123"), null);
    }

    @Test
    public void equals() {
        Password oldPass = new Password("12345678");
        Password newPass = new Password("abcdefgh");
        Password wrongPass = new Password("$$$$$$$$");
        SetPasswordCommand addOldPassCommand = new SetPasswordCommand(oldPass, newPass);
        SetPasswordCommand wrongPassCommand = new SetPasswordCommand(wrongPass, newPass);

        // same object -> returns true
        assertTrue(addOldPassCommand.equals(addOldPassCommand));

        // same values -> returns true
        SetPasswordCommand addOldPassCommandCopy = new SetPasswordCommand(oldPass, newPass);
        assertTrue(addOldPassCommand.equals(addOldPassCommandCopy));

        // different types -> returns false
        assertFalse(addOldPassCommand.equals(1));

        // null -> returns false
        assertFalse(addOldPassCommand.equals(null));

        // different loan -> returns false
        assertFalse(addOldPassCommand.equals(wrongPassCommand));
    }

    @Test
    public void execute_newPasswordAcceptedByModel_setPassSuccessful() throws Exception {
        Password currentPass = new Password("a12345");
        Password newPass = new Password("abcdefgh");
        ModelStubWithPassword modelStub = new ModelStubWithPassword();

        CommandResult commandResult = new SetPasswordCommand(currentPass, newPass).execute(modelStub, commandHistory);

        assertEquals(String.format(SetPasswordCommand.MESSAGE_CHANGE_PASSWORD_SUCCESS),
                 commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_wrongCurrentPassword_throwsCommandException() throws Exception {
        Password wrongPass = new Password("xxxxxxxx");
        Password newPass = new Password("abcdefgh");
        SetPasswordCommand setPasswordCommand = new SetPasswordCommand(wrongPass, newPass);
        ModelStubWithPassword modelStub = new ModelStubWithPassword();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_OLD_PASS);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sameCurrentPassword_throwsCommandException() throws Exception {
        ModelStubWithPassword modelStub = new ModelStubWithPassword();
        String pass = "a12345";

        SetPasswordCommand setPasswordCommand =
                new SetPasswordCommand(new Password(pass), new Password(pass));

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SAME_AS_CURRENT_PASSWORD);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    /**
     * A Model stub with a functional setPass() and getPass().
     */
    private class ModelStubWithPassword extends ModelStub {
        private Password currPass = new Password("a12345");

        @Override
        public void setPass(Password pass) {
            currPass = pass;
        }

        @Override
        public String getPass() {
            return currPass.hashedPassword();
        }

        @Override
        public LoanId getNextAvailableId() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasNextAvailableId() {
            throw new AssertionError("This method should not be called.");
        }
    }
}

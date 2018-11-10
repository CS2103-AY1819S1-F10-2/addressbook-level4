package loanbook.logic.commands;

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
import loanbook.model.loan.LoanId;
import loanbook.testutil.ModelStub;

public class SetPasswordCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private String dummySalt = "1";

    @Test
    public void constructor_nullOldPass_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetPasswordCommand(null, "123");
    }

    @Test
    public void constructor_nullNewPass_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SetPasswordCommand("123", null);
    }

    @Test
    public void equals() {
        String oldPass = "12345678";
        String newPass = "abcdefgh";
        String wrongPass = "$$$$$$$$";
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
        assertFalse(addOldPassCommand == null);

        // different loan -> returns false
        assertFalse(addOldPassCommand.equals(wrongPassCommand));
    }

    @Test
    public void execute_newPasswordAcceptedByModel_setPassSuccessful() throws Exception {
        String currentPass = "a12345";
        String newPass = "abcdefgh";
        ModelStubWithPassword modelStub = new ModelStubWithPassword();

        CommandResult commandResult = new SetPasswordCommand(currentPass, newPass).execute(modelStub, commandHistory);

        assertEquals(String.format(SetPasswordCommand.MESSAGE_CHANGE_PASSWORD_SUCCESS),
                 commandResult.feedbackToUser);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_wrongCurrentPassword_throwsCommandException() throws Exception {
        String wrongPass = "xxxxxxxx";
        String newPass = "abcdefgh";
        SetPasswordCommand setPasswordCommand = new SetPasswordCommand(wrongPass, newPass);
        ModelStubWithPassword modelStub = new ModelStubWithPassword();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PASSWORD);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sameCurrentPassword_throwsCommandException() throws Exception {
        ModelStubWithPassword modelStub = new ModelStubWithPassword();
        String pass = "a12345";

        SetPasswordCommand setPasswordCommand =
                new SetPasswordCommand(pass, pass);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SAME_AS_CURRENT_PASSWORD);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    /**
     * A Model stub with a functional setPass() and getPass().
     */
    private class ModelStubWithPassword extends ModelStub {
        private Password currPass = new Password("a12345", dummySalt);

        @Override
        public void setPass(Password pass) {
            currPass = pass;
        }

        @Override
        public String getPass() {
            return currPass.hashedPassword();
        }

        @Override
        public String getSalt() {
            return dummySalt;
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

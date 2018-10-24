package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

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
        SetPasswordCommandTest.ModelStub modelStub = new SetPasswordCommandTest.ModelStub();

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
        SetPasswordCommandTest.ModelStub modelStub = new SetPasswordCommandTest.ModelStub();

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_OLD_PASS);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_sameCurrentPassword_throwsCommandException() throws Exception {
        SetPasswordCommandTest.ModelStub modelStub = new SetPasswordCommandTest.ModelStub();
        String pass = "a12345";

        SetPasswordCommand setPasswordCommand =
                new SetPasswordCommand(new Password(pass), new Password(pass));

        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_SAME_AS_CURRENT_PASSWORD);
        setPasswordCommand.execute(modelStub, commandHistory);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private Password currPass = new Password("a12345");

        @Override
        public void resetData(ReadOnlyLoanBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLoanBook getLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBike(Bike bike) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBike(Bike bike) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBike(Bike target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateBike(Bike target, Bike editedBike) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Bike> getFilteredBikeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBikeList(Predicate<Bike> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteLoan(Loan target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLoan(Loan target, Loan editedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Loan> getFilteredLoanList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredLoanList(Predicate<Loan> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitLoanBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPass(Password pass) {
            currPass = pass;
        }

        @Override
        public String getPass() {
            return currPass.hashedPassword();
        }
    }
}

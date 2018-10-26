package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoanBook;
import seedu.address.model.Model;
import seedu.address.model.Password;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.testutil.LoanBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_loanAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingLoanAdded modelStub = new ModelStubAcceptingLoanAdded();
        Loan validLoan = new LoanBuilder().build();

        CommandResult commandResult = new AddCommand(validLoan).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLoan), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLoan), modelStub.loansAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateLoan_throwsCommandException() throws Exception {
        Loan validLoan = new LoanBuilder().build();
        AddCommand addCommand = new AddCommand(validLoan);
        ModelStub modelStub = new ModelStubWithLoan(validLoan);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_LOAN);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Loan alice = new LoanBuilder().withName("Alice").build();
        Loan bob = new LoanBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different loan -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getPass() {
            throw new AssertionError("This method should not be called.");
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

    /**
     * A Model stub that contains a single loan.
     */
    private class ModelStubWithLoan extends ModelStub {
        private final Loan loan;

        ModelStubWithLoan(Loan loan) {
            requireNonNull(loan);
            this.loan = loan;
        }

        @Override
        public boolean hasLoan(Loan loan) {
            requireNonNull(loan);
            return this.loan.isSame(loan);
        }
    }

    /**
     * A Model stub that always accept the loan being added.
     */
    private class ModelStubAcceptingLoanAdded extends ModelStub {
        final ArrayList<Loan> loansAdded = new ArrayList<>();

        @Override
        public boolean hasLoan(Loan loan) {
            requireNonNull(loan);
            return loansAdded.stream().anyMatch(loan::isSame);
        }

        @Override
        public void addLoan(Loan loan) {
            requireNonNull(loan);
            loansAdded.add(loan);
        }

        @Override
        public void commitLoanBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyLoanBook getLoanBook() {
            return new LoanBook();
        }
    }

}

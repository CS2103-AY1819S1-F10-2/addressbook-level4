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
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Loan validLoan = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validLoan).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLoan), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLoan), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        Loan validLoan = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validLoan);
        ModelStub modelStub = new ModelStubWithPerson(validLoan);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_LOAN);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Loan alice = new PersonBuilder().withName("Alice").build();
        Loan bob = new PersonBuilder().withName("Bob").build();
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
        public void addLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasLoan(Loan loan) {
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
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single loan.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Loan loan;

        ModelStubWithPerson(Loan loan) {
            requireNonNull(loan);
            this.loan = loan;
        }

        @Override
        public boolean hasLoan(Loan loan) {
            requireNonNull(loan);
            return this.loan.isSameLoan(loan);
        }
    }

    /**
     * A Model stub that always accept the loan being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Loan> personsAdded = new ArrayList<>();

        @Override
        public boolean hasLoan(Loan loan) {
            requireNonNull(loan);
            return personsAdded.stream().anyMatch(loan::isSameLoan);
        }

        @Override
        public void addLoan(Loan loan) {
            requireNonNull(loan);
            personsAdded.add(loan);
        }

        @Override
        public void commitAddressBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}

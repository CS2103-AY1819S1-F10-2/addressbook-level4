package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.Name;
import seedu.address.testutil.LoanBuilder;
import seedu.address.testutil.ModelStub;

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
        public Optional<Bike> getBike(String bikeName) {
            Bike bike = new Bike(new Name(bikeName));
            return Optional.of(bike);
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

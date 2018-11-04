package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.logic.commands.CommandTestUtil.NOEXIST_NAME_BIKE;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.LoanBook;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.UserPrefs;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.Name;
import loanbook.testutil.LoanBuilder;
import loanbook.testutil.ModelStub;

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
        Loan validLoan = new LoanBuilder()
                .withLoanId(ModelStubAcceptingLoanAdded.FIXED_LOAN_ID.toString())
                .build();

        CommandResult commandResult = new AddCommand(validLoan).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validLoan), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validLoan), modelStub.loansAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_bikeDoesNotExistInModel_throwsCommandException() throws Exception {
        Loan invalidBikeLoan = new LoanBuilder().withBike(NOEXIST_NAME_BIKE).build();
        AddCommand addCommand = new AddCommand(invalidBikeLoan);
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_BIKE_NOT_FOUND);
        addCommand.execute(model, commandHistory);
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
        assertFalse(addAliceCommand == null);

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
    private static class ModelStubAcceptingLoanAdded extends ModelStub {
        private static final LoanId FIXED_LOAN_ID = LoanId.fromInt(0);
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
        public boolean hasNextAvailableId() {
            return true;
        }

        @Override
        public LoanId getNextAvailableId() {
            return FIXED_LOAN_ID;
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

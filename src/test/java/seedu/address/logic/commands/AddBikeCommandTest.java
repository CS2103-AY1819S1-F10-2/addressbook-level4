package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.testutil.BikeBuilder;
import seedu.address.testutil.ModelStub;

public class AddBikeCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBikeCommand(null);
    }

    @Test
    public void execute_bikeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBikeAdded modelStub = new ModelStubAcceptingBikeAdded();
        Bike validBike = new BikeBuilder().build();

        CommandResult commandResult = new AddBikeCommand(validBike).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBikeCommand.MESSAGE_SUCCESS, validBike), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validBike), modelStub.bikesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateBike_throwsCommandException() throws Exception {
        Bike validBike = new BikeBuilder().build();
        AddBikeCommand addBikeCommand = new AddBikeCommand(validBike);
        ModelStub modelStub = new ModelStubWithBike(validBike);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddBikeCommand.MESSAGE_DUPLICATE_BIKE);
        addBikeCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Bike bike1 = new BikeBuilder().withName("Bike001").build();
        Bike bike2 = new BikeBuilder().withName("Bike002").build();
        AddBikeCommand addBike1Command = new AddBikeCommand(bike1);
        AddBikeCommand addBike2Command = new AddBikeCommand(bike2);

        // null -> returns false
        assertFalse(addBike1Command == null);

        // different types -> returns false
        assertFalse(addBike1Command.equals(1));

        // different bike -> returns false
        assertFalse(addBike1Command.equals(addBike2Command));

        // same object -> returns true
        assertTrue(addBike1Command.equals(addBike1Command));

        // same values -> returns true
        AddBikeCommand addAliceCommandCopy = new AddBikeCommand(bike1);
        assertTrue(addBike1Command.equals(addAliceCommandCopy));
    }

    /**
     * A Model stub that contains a single bike.
     */
    private class ModelStubWithBike extends ModelStub {
        private final Bike bike;

        ModelStubWithBike(Bike bike) {
            requireNonNull(bike);
            this.bike = bike;
        }

        @Override
        public boolean hasBike(Bike bike) {
            requireNonNull(bike);
            return this.bike.isSameBike(bike);
        }
    }

    /**
     * A Model stub that always accept the bike being added.
     */
    private class ModelStubAcceptingBikeAdded extends ModelStub {
        private final ArrayList<Bike> bikesAdded = new ArrayList<>();

        @Override
        public boolean hasBike(Bike bike) {
            requireNonNull(bike);
            return bikesAdded.stream().anyMatch(bike::isSameBike);
        }

        @Override
        public void addBike(Bike bike) {
            requireNonNull(bike);
            bikesAdded.add(bike);
        }

        @Override
        public void commitLoanBook() {
            // called by {@code AddBikeCommand#execute()}
        }

        @Override
        public ReadOnlyLoanBook getLoanBook() {
            return new LoanBook();
        }
    }
}

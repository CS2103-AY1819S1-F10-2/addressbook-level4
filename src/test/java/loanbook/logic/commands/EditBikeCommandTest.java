package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.DESC_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.DESC_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.NOEXIST_NAME_BIKE;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccessCompareEditableFields;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.EditBikeCommand.EditBikeDescriptor;
import loanbook.model.LoanBook;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;
import loanbook.testutil.BikeBuilder;
import loanbook.testutil.EditBikeDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and
 * unit tests for EditBikeCommand.
 */
public class EditBikeCommandTest {

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());

        Bike existingBike = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());
        Bike editedBike = new BikeBuilder().withName("A really unique bike name").build();

        expectedModel.updateBike(existingBike, editedBike);
        expectedModel.commitLoanBook();

        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder(editedBike).build();
        EditBikeCommand command = new EditBikeCommand(existingBike.getName(), descriptor);

        String expectedMessage = String.format(EditBikeCommand.MESSAGE_EDIT_BIKE_SUCCESS, editedBike);

        assertCommandSuccessCompareEditableFields(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        Bike existingBike = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());

        EditBikeCommand command = new EditBikeCommand(existingBike.getName(), new EditBikeDescriptor());
        Bike editedBike = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());

        String expectedMessage = String.format(EditBikeCommand.MESSAGE_EDIT_BIKE_SUCCESS, editedBike);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.commitLoanBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bikeExistsInModel_success() {
        Bike bikeInModel = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());

        BikeBuilder bikeInList = new BikeBuilder(bikeInModel);
        Bike editedBike = bikeInList.withName("An awesome new bike name").build();

        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder()
            .withName("An awesome new bike name").build();
        EditBikeCommand command = new EditBikeCommand(bikeInModel.getName(), descriptor);

        String expectedMessage = String.format(EditBikeCommand.MESSAGE_EDIT_BIKE_SUCCESS, editedBike);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateBike(bikeInModel, editedBike);
        expectedModel.commitLoanBook();

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bikeDoesNotExistInModel_failure() {
        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder()
            .withName(VALID_NAME_BIKE1).build();
        EditBikeCommand command = new EditBikeCommand(new Name(NOEXIST_NAME_BIKE), descriptor);

        assertCommandFailure(command, model, commandHistory, EditBikeCommand.MESSAGE_BIKE_NOT_FOUND);
    }

    @Test
    public void executeUndoRedo_nonexistentNameUnfilteredList_failure() {
        EditBikeDescriptor descriptor = new EditBikeDescriptorBuilder()
            .withName(VALID_NAME_BIKE1).build();
        EditBikeCommand command = new EditBikeCommand(new Name(NOEXIST_NAME_BIKE), descriptor);

        // execution failed -> loan book state not added into model
        assertCommandFailure(command, model, commandHistory, EditBikeCommand.MESSAGE_BIKE_NOT_FOUND);

        // single loan book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        final EditBikeCommand standardCommand = new EditBikeCommand(new Name(VALID_NAME_BIKE1), DESC_BIKE1);

        // same values -> returns true
        EditBikeDescriptor copyDescriptor = new EditBikeDescriptor(DESC_BIKE1);
        EditBikeCommand commandWithSameValues = new EditBikeCommand(new Name(VALID_NAME_BIKE1), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditBikeCommand(new Name(VALID_NAME_BIKE2), DESC_BIKE1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditBikeCommand(new Name(VALID_NAME_BIKE1), DESC_BIKE2)));
    }

}

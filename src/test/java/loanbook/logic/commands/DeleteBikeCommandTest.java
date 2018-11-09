package loanbook.logic.commands;

import static loanbook.commons.core.Messages.MESSAGE_BIKE_NOT_FOUND;
import static loanbook.logic.commands.CommandTestUtil.NOEXIST_NAME_BIKE;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.logic.commands.CommandTestUtil.showBikeAtIndex;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.Password;
import loanbook.model.UserPrefs;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Name;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteBikeCommand}.
 */
public class DeleteBikeCommandTest {

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validNameUnfilteredList_success() {
        Password pass = new Password("a12345");
        model.setPass(pass);

        Bike bikeToDelete = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteBikeCommand deleteBikeCommand = new DeleteBikeCommand(bikeToDelete.getName(), pass);

        String expectedMessage = String.format(DeleteBikeCommand.MESSAGE_DELETE_BIKE_SUCCESS, bikeToDelete);

        ModelManager expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteBike(bikeToDelete);
        expectedModel.commitLoanBook();

        assertCommandSuccess(deleteBikeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNameUnfilteredList_throwsCommandException() {
        DeleteBikeCommand deleteBikeCommand =
                new DeleteBikeCommand(new Name(NOEXIST_NAME_BIKE), new Password(model.getPass()));

        assertCommandFailure(deleteBikeCommand, model, commandHistory, MESSAGE_BIKE_NOT_FOUND);
    }

    @Test
    public void execute_validNameFilteredList_success() {
        Password pass = new Password("a12345");
        model.setPass(pass);

        showBikeAtIndex(model, INDEX_FIRST_LOAN);

        Bike bikeToDelete = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteBikeCommand deleteBikeCommand = new DeleteBikeCommand(bikeToDelete.getName(), pass);

        String expectedMessage = String.format(DeleteBikeCommand.MESSAGE_DELETE_BIKE_SUCCESS, bikeToDelete);

        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteBike(bikeToDelete);
        expectedModel.commitLoanBook();
        showNoBike(expectedModel);

        assertCommandSuccess(deleteBikeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bikeNotInFilteredList_success() {
        Password pass = new Password("a12345");
        model.setPass(pass);

        // Have at least 2 bikes in the list
        assertTrue(model.getLoanBook().getBikeList().size() >= 2);

        // Grab a valid bike in the model, but that's not in the filtered list
        Bike bikeToDelete = model.getFilteredBikeList().get(INDEX_SECOND_LOAN.getZeroBased());

        showBikeAtIndex(model, INDEX_FIRST_LOAN);

        DeleteBikeCommand deleteBikeCommand = new DeleteBikeCommand(bikeToDelete.getName(), pass);

        String expectedMessage = String.format(DeleteBikeCommand.MESSAGE_DELETE_BIKE_SUCCESS, bikeToDelete);

        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteBike(bikeToDelete);
        expectedModel.commitLoanBook();
        showBikeAtIndex(expectedModel, INDEX_FIRST_LOAN);

        assertCommandSuccess(deleteBikeCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoRedo_validNameUnfilteredList_success() throws Exception {
        Password pass = new Password("a12345");
        model.setPass(pass);

        Bike bikeToDelete = model.getFilteredBikeList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteBikeCommand deleteBikeCommand = new DeleteBikeCommand(bikeToDelete.getName(), pass);
        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteBike(bikeToDelete);
        expectedModel.commitLoanBook();

        // delete -> first bike deleted
        deleteBikeCommand.execute(model, commandHistory);

        // undo -> reverts loanbook back to previous state and filtered bike list to show all bikes
        expectedModel.undoLoanBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first bike deleted again
        expectedModel.redoLoanBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidBikeName_failure() {
        Password pass = new Password("a12345");
        model.setPass(pass);

        DeleteBikeCommand deleteBikeCommand = new DeleteBikeCommand(new Name(NOEXIST_NAME_BIKE), pass);

        // execution failed -> loan book state not added into model
        assertCommandFailure(deleteBikeCommand, model, commandHistory, MESSAGE_BIKE_NOT_FOUND);

        // single loan book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    @Test
    public void equals() {
        Password pass = new Password("a12345");
        model.setPass(pass);

        DeleteBikeCommand deleteFirstCommand = new DeleteBikeCommand(new Name(VALID_NAME_BIKE1), pass);
        DeleteBikeCommand deleteSecondCommand = new DeleteBikeCommand(new Name(VALID_NAME_BIKE2), pass);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteBikeCommand deleteFirstCommandCopy = new DeleteBikeCommand(new Name(VALID_NAME_BIKE1), pass);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand == null);

        // different loan -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoBike(Model model) {
        model.updateFilteredBikeList(p -> false);

        assertTrue(model.getFilteredBikeList().isEmpty());
    }
}

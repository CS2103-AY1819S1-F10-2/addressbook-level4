package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showLoanAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static seedu.address.testutil.TypicalLoans.getTypicalLoanBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.loan.Loan;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete);

        ModelManager expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showLoanAtIndex(model, INDEX_FIRST_LOAN);

        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete);

        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();
        showNoLoan(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showLoanAtIndex(model, INDEX_FIRST_LOAN);

        Index outOfBoundIndex = INDEX_SECOND_LOAN;
        // ensures that outOfBoundIndex is still in bounds of loan book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLoanBook().getLoanList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN);
        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();

        // delete -> first loan deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts loanbook back to previous state and filtered loan list to show all loans
        expectedModel.undoLoanBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first loan deleted again
        expectedModel.redoLoanBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> loan book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        // single loan book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Loan} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted loan in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the loan object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameLoanDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN);
        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());

        showLoanAtIndex(model, INDEX_SECOND_LOAN);
        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();

        // delete -> deletes second loan in unfiltered loan list / first loan in filtered loan list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts loanbook back to previous state and filtered loan list to show all loans
        expectedModel.undoLoanBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(loanToDelete, model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased()));
        // redo -> deletes same second loan in unfiltered loan list
        expectedModel.redoLoanBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LOAN);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LOAN);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LOAN);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different loan -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoLoan(Model model) {
        model.updateFilteredLoanList(p -> false);

        assertTrue(model.getFilteredLoanList().isEmpty());
    }
}

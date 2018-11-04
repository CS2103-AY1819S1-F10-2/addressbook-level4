package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.logic.commands.CommandTestUtil.showLoanAtIndex;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.Password;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Loan;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        String pass = "a12345";
        model.setPass(new Password(pass));

        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete);

        ModelManager expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, new Password(model.getPass()));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        String pass = "a12345";
        model.setPass(new Password(pass));

        showLoanAtIndex(model, INDEX_FIRST_LOAN);

        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_LOAN_SUCCESS, loanToDelete);

        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.deleteLoan(loanToDelete);
        expectedModel.commitLoanBook();
        showNoLoan(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        String pass = "a12345";
        model.setPass(new Password(pass));

        showLoanAtIndex(model, INDEX_FIRST_LOAN);

        Index outOfBoundIndex = INDEX_SECOND_LOAN;
        // ensures that outOfBoundIndex is still in bounds of loan book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLoanBook().getLoanList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, new Password(pass));

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        String pass = "a12345";
        model.setPass(new Password(pass));

        Loan loanToDelete = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));
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
        String pass = "a12345";
        model.setPass(new Password(pass));

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex, new Password(pass));

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
        String pass = "a12345";
        model.setPass(new Password(pass));

        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));
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
        String pass = "a12345";
        model.setPass(new Password(pass));

        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_LOAN, new Password(pass));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_LOAN, new Password(pass));
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
    private void showNoLoan(Model model) {
        model.updateFilteredLoanList(p -> false);

        assertTrue(model.getFilteredLoanList().isEmpty());
    }
}

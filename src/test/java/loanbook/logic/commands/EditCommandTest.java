package loanbook.logic.commands;

import static loanbook.commons.core.Messages.MESSAGE_BIKE_NOT_FOUND;
import static loanbook.logic.commands.CommandTestUtil.DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.NOEXIST_NAME_BIKE;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE4;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccessCompareEditableFields;
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
import loanbook.logic.commands.EditCommand.EditLoanDescriptor;
import loanbook.model.LoanBook;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Loan;
import loanbook.testutil.EditLoanDescriptorBuilder;
import loanbook.testutil.LoanBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());

        // Have the edited loan have the default fields in the LoanBuilder.
        // However, the uneditable fields should match the existing Loan.
        //
        // Note: Without these, assertCommandSuccessCompareEditableFields will
        // actually find that model.hasEqualEditableFields(expectedModel) is true.
        // However, the expected messages are different, which is why it fails.
        Loan existingLoan = model.getFilteredLoanList().get(0);
        Loan editedLoan = new LoanBuilder()
                .withLoanId(existingLoan.getLoanId().toString())
                .withLoanStartTime(existingLoan.getLoanStartTime().toString())
                .withLoanEndTime(existingLoan.getLoanEndTime().toString())
                .withLoanStatus(existingLoan.getLoanStatus().name())
                .build();

        expectedModel.updateLoan(model.getFilteredLoanList().get(0), editedLoan);
        expectedModel.commitLoanBook();

        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        assertCommandSuccessCompareEditableFields(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastLoan = Index.fromOneBased(model.getFilteredLoanList().size());
        Loan lastLoan = model.getFilteredLoanList().get(indexLastLoan.getZeroBased());

        LoanBuilder loanInList = new LoanBuilder(lastLoan);
        Loan editedLoan = loanInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastLoan, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateLoan(lastLoan, editedLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, new EditLoanDescriptor());
        Loan editedLoan = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.commitLoanBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showLoanAtIndex(model, INDEX_FIRST_LOAN);

        Loan loanInFilteredList = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        Loan editedLoan = new LoanBuilder(loanInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN,
                new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateLoan(model.getFilteredLoanList().get(0), editedLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bikeExistsInModel_success() {
        Index indexLastLoan = Index.fromOneBased(model.getFilteredLoanList().size());
        Loan lastLoan = model.getFilteredLoanList().get(indexLastLoan.getZeroBased());

        LoanBuilder loanInList = new LoanBuilder(lastLoan);
        Loan editedLoan = loanInList.withBike(VALID_NAME_BIKE4).build();

        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder()
            .withBike(VALID_NAME_BIKE4).build();
        EditCommand editCommand = new EditCommand(indexLastLoan, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateLoan(lastLoan, editedLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_bikeDoesNotExistInModel_failure() {
        Index indexLastLoan = Index.fromOneBased(model.getFilteredLoanList().size());

        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder()
            .withBike(NOEXIST_NAME_BIKE).build();
        EditCommand editCommand = new EditCommand(indexLastLoan, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, MESSAGE_BIKE_NOT_FOUND);
    }

    @Test
    public void execute_invalidLoanIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of loan book
     */
    @Test
    public void execute_invalidLoanIndexFilteredList_failure() {
        showLoanAtIndex(model, INDEX_FIRST_LOAN);
        Index outOfBoundIndex = INDEX_SECOND_LOAN;
        // ensures that outOfBoundIndex is still in bounds of loan book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getLoanBook().getLoanList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Loan editedLoan = new LoanBuilder().build();
        Loan loanToEdit = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);
        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());
        expectedModel.updateLoan(loanToEdit, editedLoan);
        expectedModel.commitLoanBook();

        // edit -> first loan edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts loanbook back to previous state and filtered loan list to show all loans
        expectedModel.undoLoanBook();
        assertCommandSuccessCompareEditableFields(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first loan edited again
        expectedModel.redoLoanBook();
        assertCommandSuccessCompareEditableFields(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> loan book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        // single loan book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Loan} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited loan in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the loan object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameLoanEdited() throws Exception {
        Loan editedLoan = new LoanBuilder().build();
        EditLoanDescriptor descriptor = new EditLoanDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);
        Model expectedModel = new ModelManager(new LoanBook(model.getLoanBook()), new UserPrefs());

        showLoanAtIndex(model, INDEX_SECOND_LOAN);
        Loan loanToEdit = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        expectedModel.updateLoan(loanToEdit, editedLoan);
        expectedModel.commitLoanBook();

        // edit -> edits second loan in unfiltered loan list / first loan in filtered loan list
        editCommand.execute(model, commandHistory);

        // undo -> reverts loanbook back to previous state and filtered loan list to show all loans
        expectedModel.undoLoanBook();
        assertCommandSuccessCompareEditableFields(new UndoCommand(), model, commandHistory,
                UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased()), loanToEdit);
        // redo -> edits same second loan in unfiltered loan list
        expectedModel.redoLoanBook();
        assertCommandSuccessCompareEditableFields(new RedoCommand(), model, commandHistory,
                RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_LOAN, DESC_AMY);

        // same values -> returns true
        EditLoanDescriptor copyDescriptor = new EditLoanDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_LOAN, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand == null);

        // different types -> returns false
        assertFalse(standardCommand.equals(new ListCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_LOAN, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LOAN, DESC_BOB)));
    }

}

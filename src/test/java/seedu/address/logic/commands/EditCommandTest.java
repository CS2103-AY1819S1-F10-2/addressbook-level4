package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static seedu.address.testutil.TypicalLoans.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditLoanDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Loan;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Loan editedLoan = new PersonBuilder().build();
        EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateLoan(model.getFilteredLoanList().get(0), editedLoan);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredLoanList().size());
        Loan lastLoan = model.getFilteredLoanList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastLoan);
        Loan editedLoan = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateLoan(lastLoan, editedLoan);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, new EditCommand.EditLoanDescriptor());
        Loan editedLoan = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_LOAN);

        Loan loanInFilteredList = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        Loan editedLoan = new PersonBuilder(loanInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateLoan(model.getFilteredLoanList().get(0), editedLoan);
        expectedModel.commitAddressBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Loan firstLoan = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        EditCommand.EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder(firstLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_LOAN, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_LOAN);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_LOAN);

        // edit loan in filtered list into a duplicate in address book
        Loan loanInList = model.getAddressBook().getLoanList().get(INDEX_SECOND_LOAN.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN,
                new EditPersonDescriptorBuilder(loanInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_LOAN);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_LOAN);
        Index outOfBoundIndex = INDEX_SECOND_LOAN;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getLoanList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Loan editedLoan = new PersonBuilder().build();
        Loan loanToEdit = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        EditCommand.EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateLoan(loanToEdit, editedLoan);
        expectedModel.commitAddressBook();

        // edit -> first loan edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered loan list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first loan edited again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredLoanList().size() + 1);
        EditCommand.EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
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
    public void executeUndoRedo_validIndexFilteredList_samePersonEdited() throws Exception {
        Loan editedLoan = new PersonBuilder().build();
        EditCommand.EditLoanDescriptor descriptor = new EditPersonDescriptorBuilder(editedLoan).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_LOAN, descriptor);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_LOAN);
        Loan loanToEdit = model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased());
        expectedModel.updateLoan(loanToEdit, editedLoan);
        expectedModel.commitAddressBook();

        // edit -> edits second loan in unfiltered loan list / first loan in filtered loan list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered loan list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased()), loanToEdit);
        // redo -> edits same second loan in unfiltered loan list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_LOAN, DESC_AMY);

        // same values -> returns true
        EditLoanDescriptor copyDescriptor = new EditCommand.EditLoanDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_LOAN, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_LOAN, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_LOAN, DESC_BOB)));
    }

}

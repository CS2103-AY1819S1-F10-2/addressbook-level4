package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_LOAN_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TestUtil.getLoan;
import static seedu.address.testutil.TestUtil.getMidIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static seedu.address.testutil.TypicalLoans.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.loan.Loan;

public class DeleteCommandSystemTest extends LoanBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first loan in the list, command with leading spaces and trailing spaces -> deleted */
        Model expectedModel = getModel();
        String command = "     "
                + DeleteCommand.COMMAND_WORD
                + " i/"
                + INDEX_FIRST_LOAN.getOneBased()
                + " x/a12345"
                + "       ";
        Loan deletedLoan = removeLoan(expectedModel, INDEX_FIRST_LOAN);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LOAN_SUCCESS, deletedLoan);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last loan in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastLoanIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastLoanIndex);

        /* Case: undo deleting the last loan in the list -> last loan restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last loan in the list -> last loan deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeLoan(modelBeforeDeletingLast, lastLoanIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle loan in the list -> deleted */
        Index middleLoanIndex = getMidIndex(getModel());
        assertCommandSuccess(middleLoanIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered loan list, delete index within bounds of loan book and loan list -> deleted */
        showLoansWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_LOAN;
        assertTrue(index.getZeroBased() < getModel().getFilteredLoanList().size());
        assertCommandSuccess(index);

        /* Case: filtered loan list, delete index within bounds of loan book but out of bounds of loan list
         * -> rejected
         */
        showLoansWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getLoanBook().getLoanList().size();
        command = DeleteCommand.COMMAND_WORD + " i/" + invalidIndex + " x/a12345";
        assertCommandFailure(command, MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a loan card is selected ------------------------ */

        /* Case: delete the selected loan -> loan list panel selects the loan before the deleted loan */
        showAllLoans();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectLoan(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " i/" + selectedIndex.getOneBased() + " x/a12345";
        deletedLoan = removeLoan(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_LOAN_SUCCESS, deletedLoan);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " i/0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteCommand.COMMAND_WORD + " i/-1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getLoanBook().getLoanList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " i/" + outOfBoundsIndex.getOneBased() + " x/a12345";
        assertCommandFailure(command, MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " i/abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " i/1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Removes the {@code Loan} at the specified {@code index} in {@code model}'s loan book.
     * @return the removed loan
     */
    private Loan removeLoan(Model model, Index index) {
        Loan targetLoan = getLoan(model, index);
        model.deleteLoan(targetLoan);
        return targetLoan;
    }

    /**
     * Deletes the loan at {@code toDelete} by creating a default {@code DeleteCommand} using {@code toDelete} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDelete) {
        Model expectedModel = getModel();
        Loan deletedLoan = removeLoan(expectedModel, toDelete);
        String expectedResultMessage = String.format(MESSAGE_DELETE_LOAN_SUCCESS, deletedLoan);

        assertCommandSuccess(DeleteCommand.COMMAND_WORD
                 + " i/" + toDelete.getOneBased()
                 + " x/a12345", expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card remains unchanged.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see LoanBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

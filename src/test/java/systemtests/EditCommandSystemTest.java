package systemtests;

import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.BIKE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.INVALID_BIKE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_LOANRATE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_NRIC_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static loanbook.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static loanbook.logic.commands.CommandTestUtil.LOANRATE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.LOANRATE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.NRIC_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.NRIC_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static loanbook.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static loanbook.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static loanbook.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static loanbook.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANRATE_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_LOANSTARTTIME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static loanbook.logic.parser.CliSyntax.PREFIX_TAG;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;
import static loanbook.testutil.TypicalIndexes.INDEX_FIRST_LOAN;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.testutil.TypicalLoans.AMY;
import static loanbook.testutil.TypicalLoans.BOB;
import static loanbook.testutil.TypicalLoans.KEYWORD_MATCHING_MEIER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import loanbook.commons.core.Messages;
import loanbook.commons.core.index.Index;
import loanbook.logic.commands.EditCommand;
import loanbook.logic.commands.RedoCommand;
import loanbook.logic.commands.UndoCommand;
import loanbook.model.Model;
import loanbook.model.loan.Email;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanRate;
import loanbook.model.loan.Name;
import loanbook.model.loan.Nric;
import loanbook.model.loan.Phone;
import loanbook.model.tag.Tag;
import loanbook.testutil.LoanBuilder;
import loanbook.testutil.LoanUtil;

public class EditCommandSystemTest extends LoanBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_LOAN;
        String command = " " + EditCommand.COMMAND_WORD + "  " + index.getOneBased() + "  " + NAME_DESC_BOB + "  "
                + NRIC_DESC_BOB + " " + PHONE_DESC_BOB + " " + EMAIL_DESC_BOB + "  "
                + BIKE_DESC_BOB + " " + LOANRATE_DESC_BOB + " " + TAG_DESC_HUSBAND + " ";
        Loan editedLoan = new LoanBuilder(BOB).withTags(VALID_TAG_HUSBAND).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: undo editing the last loan in the list -> last loan restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last loan in the list -> last loan edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateLoan(
                getModel().getFilteredLoanList().get(INDEX_FIRST_LOAN.getZeroBased()), editedLoan);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a loan with new values same as existing values -> edited */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandSuccess(command, index, BOB);

        /* Case: edit a loan with new values same as another loan's values but with different name -> edited */
        assertTrue(getModel().getLoanBook().getLoanList().contains(BOB));
        index = INDEX_SECOND_LOAN;
        assertNotEquals(getModel().getFilteredLoanList().get(index.getZeroBased()), BOB);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedLoan = new LoanBuilder(BOB).withName(VALID_NAME_AMY).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: edit a loan with new values same as another loan's values but with different nric -> edited */
        index = INDEX_SECOND_LOAN;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_AMY + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedLoan = new LoanBuilder(BOB).withNric(VALID_NRIC_AMY).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: edit a loan with new values same as another loan's values but with different bike -> edited */
        index = INDEX_SECOND_LOAN;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_AMY + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedLoan = new LoanBuilder(BOB).withBike(VALID_NAME_BIKE1).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: edit a loan with new values same as another loan's values but with different phone number, email,
        loan rate and loan time -> edited */
        index = INDEX_SECOND_LOAN;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + BIKE_DESC_BOB + LOANRATE_DESC_AMY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        editedLoan = new LoanBuilder(BOB).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withLoanRate(VALID_LOANRATE_AMY).withLoanStartTime(VALID_LOANSTARTTIME_AMY).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_LOAN;
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Loan loanToEdit = getModel().getFilteredLoanList().get(index.getZeroBased());
        editedLoan = new LoanBuilder(loanToEdit).withTags().build();
        assertCommandSuccess(command, index, editedLoan);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered loan list, edit index within bounds of loan book and loan list -> edited */
        showLoansWithName(KEYWORD_MATCHING_MEIER);
        index = INDEX_FIRST_LOAN;
        assertTrue(index.getZeroBased() < getModel().getFilteredLoanList().size());
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + NAME_DESC_BOB;
        loanToEdit = getModel().getFilteredLoanList().get(index.getZeroBased());
        editedLoan = new LoanBuilder(loanToEdit).withName(VALID_NAME_BOB).build();
        assertCommandSuccess(command, index, editedLoan);

        /* Case: filtered loan list, edit index within bounds of loan book but out of bounds of loan list
         * -> rejected
         */
        showLoansWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getLoanBook().getLoanList().size();
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a loan card is selected -------------------------- */

        /* Case: selects first card in the loan list, edit a loan -> edited, card selection remains unchanged but
         * browser url changes
         */
        showAllLoans();
        index = INDEX_FIRST_LOAN;
        selectLoan(index);
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + NRIC_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + BIKE_DESC_AMY + LOANRATE_DESC_AMY
                + TAG_DESC_FRIEND;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new loan's name
        assertCommandSuccess(command, index, AMY, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " 0" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " -1" + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredLoanList().size() + 1;
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + invalidIndex + NAME_DESC_BOB,
                Messages.MESSAGE_INVALID_LOAN_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + NAME_DESC_BOB,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased(),
                EditCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid nric -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_NRIC_DESC,
                Nric.MESSAGE_NRIC_CONSTRAINTS);

        /* Case: invalid phone -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_PHONE_DESC,
                Phone.MESSAGE_PHONE_CONSTRAINTS);

        /* Case: invalid email -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_EMAIL_DESC,
                Email.MESSAGE_EMAIL_CONSTRAINTS);

        /* Case: invalid bike -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_BIKE_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);

        /* Case: invalid loan rate -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_LOANRATE_DESC,
                LoanRate.MESSAGE_LOANRATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_LOAN.getOneBased() + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a loan with new values same as another loan's values -> rejected */
        executeCommand(LoanUtil.getAddCommand(BOB));
        assertTrue(getModel().getLoanBook().getLoanList().contains(BOB));
        index = INDEX_FIRST_LOAN;
        assertFalse(getModel().getFilteredLoanList().get(index.getZeroBased()).equals(BOB));
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);

        /* Case: edit a loan with new values same as another loan's values but with different tags -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);

        /* Case: edit a loan with new values same as another loan's values but with different phone -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_AMY
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);

        /* Case: edit a loan with new values same as another loan's values but with different email -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_AMY
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);

        /* Case: edit a loan with new values same as another loan's values but with different loan rate -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_AMY
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);

        /* Case: edit a loan with new values same as another loan's values but with different loan time -> rejected */
        command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_BOB + NRIC_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + BIKE_DESC_BOB + LOANRATE_DESC_BOB
                + TAG_DESC_FRIEND + TAG_DESC_HUSBAND;
        assertCommandFailure(command, EditCommand.MESSAGE_DUPLICATE_LOAN);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Loan, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, Loan, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Loan editedLoan) {
        assertCommandSuccess(command, toEdit, editedLoan, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the loan at index {@code toEdit} being
     * updated to values specified {@code editedLoan}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Loan editedLoan,
            Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateLoan(expectedModel.getFilteredLoanList().get(toEdit.getZeroBased()), editedLoan);
        expectedModel.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_LOAN_SUCCESS, editedLoan), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see LoanBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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

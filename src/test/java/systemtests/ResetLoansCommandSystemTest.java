package systemtests;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static loanbook.commons.core.Messages.MESSAGE_INVALID_PASSWORD;
import static loanbook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static loanbook.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static loanbook.testutil.TypicalBikes.getTypicalBikes;
import static loanbook.testutil.TypicalLoans.KEYWORD_MATCHING_MEIER;

import org.junit.Test;

import loanbook.commons.core.index.Index;
import loanbook.logic.commands.RedoCommand;
import loanbook.logic.commands.ResetLoansCommand;
import loanbook.logic.commands.UndoCommand;
import loanbook.model.Model;
import loanbook.model.ModelManager;

public class ResetLoansCommandSystemTest extends LoanBookSystemTest {

    /**
     * The clear command resets the loans and loan ID only.
     * So this model should have 0 Loans, a LastID of {@code NO_LAST_USED_ID_VALUE} and the typical Bikes.
     */
    private static final Model clearedModel;
    static {
        clearedModel = new ModelManager();
        clearedModel.setBikes(getTypicalBikes());
    }

    @Test
    public void resetLoans() {
        final Model defaultModel = getModel();
        String defaultPassword = "a12345";
        String defaultResetLoansCommand = ResetLoansCommand.COMMAND_WORD + " " + PREFIX_PASSWORD + defaultPassword;

        /* Case: no password -> rejected */
        assertCommandFailure(ResetLoansCommand.COMMAND_WORD, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ResetLoansCommand.MESSAGE_USAGE));

        /* Case: reset loans of non-empty loan book, command with leading spaces -> loans reset
         */
        assertCommandSuccess("   " + defaultResetLoansCommand);
        assertSelectedCardUnchanged();

        /* Case: undo loans reset -> original loan book restored */
        String command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, defaultModel);
        assertSelectedCardUnchanged();

        /* Case: redo resetting loans of loan book -> loans reset */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, clearedModel);
        assertSelectedCardUnchanged();

        /* Case: selects first card in loan list and reset loans -> loans reset and no card selected */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original loan book
        selectLoan(Index.fromOneBased(1));
        assertCommandSuccess(defaultResetLoansCommand);
        assertSelectedCardDeselected();

        /* Case: filters the loan list before resetting loans -> loans reset */
        executeCommand(UndoCommand.COMMAND_WORD); // restores the original loan book
        showLoansWithName(KEYWORD_MATCHING_MEIER);
        assertCommandSuccess(defaultResetLoansCommand);
        assertSelectedCardUnchanged();

        /* Case: reset loans of empty loan book -> loans reset */
        assertCommandSuccess(defaultResetLoansCommand);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("REseTLoanS", MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid password -> rejected */
        assertCommandFailure(ResetLoansCommand.COMMAND_WORD + " " + PREFIX_PASSWORD + "invalidPassword",
                MESSAGE_INVALID_PASSWORD);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ResetLoansCommand#MESSAGE_RESET_LOANS_SUCCESS} and the model related components equal to a
     * model whose loans are reset.
     * These verifications are done by
     * {@code LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        assertCommandSuccess(command, ResetLoansCommand.MESSAGE_RESET_LOANS_SUCCESS, clearedModel);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ResetLoansCommandSystemTest#assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code LoanBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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

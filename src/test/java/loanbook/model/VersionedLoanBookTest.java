package loanbook.model;

import static loanbook.testutil.TypicalLoans.AMY;
import static loanbook.testutil.TypicalLoans.BOB;
import static loanbook.testutil.TypicalLoans.CARL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import loanbook.testutil.LoanBookBuilder;

public class VersionedLoanBookTest {

    private final ReadOnlyLoanBook loanBookWithAmy = new LoanBookBuilder().withLoan(AMY).build();
    private final ReadOnlyLoanBook loanBookWithBob = new LoanBookBuilder().withLoan(BOB).build();
    private final ReadOnlyLoanBook loanBookWithCarl = new LoanBookBuilder().withLoan(CARL).build();
    private final ReadOnlyLoanBook emptyLoanBook = new LoanBookBuilder().build();

    @Test
    public void commit_singleLoanBook_noStatesRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(emptyLoanBook);

        versionedLoanBook.commit();
        assertLoanBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyLoanBook),
                emptyLoanBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleLoanBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);

        versionedLoanBook.commit();
        assertLoanBookListStatus(versionedLoanBook,
                Arrays.asList(emptyLoanBook, loanBookWithAmy, loanBookWithBob),
                loanBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleLoanBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        versionedLoanBook.commit();
        assertLoanBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyLoanBook),
                emptyLoanBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleLoanBookPointerAtEndOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);

        assertTrue(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_multipleLoanBookPointerAtStartOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        assertTrue(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_singleLoanBook_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(emptyLoanBook);

        assertFalse(versionedLoanBook.canUndo());
    }

    @Test
    public void canUndo_multipleLoanBookPointerAtStartOfStateList_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertFalse(versionedLoanBook.canUndo());
    }

    @Test
    public void canRedo_multipleLoanBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        assertTrue(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_multipleLoanBookPointerAtStartOfStateList_returnsTrue() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertTrue(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_singleLoanBook_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(emptyLoanBook);

        assertFalse(versionedLoanBook.canRedo());
    }

    @Test
    public void canRedo_multipleLoanBookPointerAtEndOfStateList_returnsFalse() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);

        assertFalse(versionedLoanBook.canRedo());
    }

    @Test
    public void undo_multipleLoanBookPointerAtEndOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);

        versionedLoanBook.undo();
        assertLoanBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyLoanBook),
                loanBookWithAmy,
                Collections.singletonList(loanBookWithBob));
    }

    @Test
    public void undo_multipleLoanBookPointerNotAtStartOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        versionedLoanBook.undo();
        assertLoanBookListStatus(versionedLoanBook,
                Collections.emptyList(),
                emptyLoanBook,
                Arrays.asList(loanBookWithAmy, loanBookWithBob));
    }

    @Test
    public void undo_singleLoanBook_throwsNoUndoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(emptyLoanBook);

        assertThrows(VersionedLoanBook.NoUndoableStateException.class, versionedLoanBook::undo);
    }

    @Test
    public void undo_multipleLoanBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        assertThrows(VersionedLoanBook.NoUndoableStateException.class, versionedLoanBook::undo);
    }

    @Test
    public void redo_multipleLoanBookPointerNotAtEndOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);

        versionedLoanBook.redo();
        assertLoanBookListStatus(versionedLoanBook,
                Arrays.asList(emptyLoanBook, loanBookWithAmy),
                loanBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleLoanBookPointerAtStartOfStateList_success() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 2);

        versionedLoanBook.redo();
        assertLoanBookListStatus(versionedLoanBook,
                Collections.singletonList(emptyLoanBook),
                loanBookWithAmy,
                Collections.singletonList(loanBookWithBob));
    }

    @Test
    public void redo_singleLoanBook_throwsNoRedoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(emptyLoanBook);

        assertThrows(VersionedLoanBook.NoRedoableStateException.class, versionedLoanBook::redo);
    }

    @Test
    public void redo_multipleLoanBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(
                emptyLoanBook, loanBookWithAmy, loanBookWithBob);

        assertThrows(VersionedLoanBook.NoRedoableStateException.class, versionedLoanBook::redo);
    }

    @Test
    public void equals() {
        VersionedLoanBook versionedLoanBook = prepareLoanBookList(loanBookWithAmy, loanBookWithBob);

        // same values -> returns true
        VersionedLoanBook copy = prepareLoanBookList(loanBookWithAmy, loanBookWithBob);
        assertTrue(versionedLoanBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedLoanBook.equals(versionedLoanBook));

        // null -> returns false
        assertFalse(versionedLoanBook.equals(null));

        // different types -> returns false
        assertFalse(versionedLoanBook.equals(1));

        // different state list -> returns false
        VersionedLoanBook differentLoanBookList = prepareLoanBookList(loanBookWithBob, loanBookWithCarl);
        assertFalse(versionedLoanBook.equals(differentLoanBookList));

        // different current pointer index -> returns false
        VersionedLoanBook differentCurrentStatePointer = prepareLoanBookList(
                loanBookWithAmy, loanBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedLoanBook, 1);
        assertFalse(versionedLoanBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedLoanBook} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedLoanBook#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedLoanBook#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertLoanBookListStatus(VersionedLoanBook versionedLoanBook,
                                          List<ReadOnlyLoanBook> expectedStatesBeforePointer,
                                          ReadOnlyLoanBook expectedCurrentState,
                                          List<ReadOnlyLoanBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new LoanBook(versionedLoanBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedLoanBook.canUndo()) {
            versionedLoanBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyLoanBook expectedLoanBook : expectedStatesBeforePointer) {
            assertEquals(expectedLoanBook, new LoanBook(versionedLoanBook));
            versionedLoanBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyLoanBook expectedLoanBook : expectedStatesAfterPointer) {
            versionedLoanBook.redo();
            assertEquals(expectedLoanBook, new LoanBook(versionedLoanBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedLoanBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedLoanBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedLoanBook} with the {@code loanBookStates} added into it, and the
     * {@code VersionedLoanBook#currentStatePointer} at the end of list.
     */
    private VersionedLoanBook prepareLoanBookList(ReadOnlyLoanBook... loanBookStates) {
        assertFalse(loanBookStates.length == 0);

        VersionedLoanBook versionedLoanBook = new VersionedLoanBook(loanBookStates[0]);
        for (int i = 1; i < loanBookStates.length; i++) {
            versionedLoanBook.replaceData(loanBookStates[i]);
            versionedLoanBook.commit();
        }

        return versionedLoanBook;
    }

    /**
     * Shifts the {@code versionedLoanBook#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedLoanBook versionedLoanBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedLoanBook.undo();
        }
    }
}

package loanbook.model;

import java.util.ArrayList;
import java.util.List;

import loanbook.commons.util.CollectionUtil;
import loanbook.model.loan.Loan;

/**
 * {@code LoanBook} that keeps track of its own history.
 */
public class VersionedLoanBook extends LoanBook {

    private final List<ReadOnlyLoanBook> loanBookStateList;
    private int currentStatePointer;

    public VersionedLoanBook(ReadOnlyLoanBook initialState) {
        super(initialState);

        loanBookStateList = new ArrayList<>();
        loanBookStateList.add(new LoanBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code LoanBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        loanBookStateList.add(new LoanBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        loanBookStateList.subList(currentStatePointer + 1, loanBookStateList.size()).clear();
    }

    /**
     * Restores the loan book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        replaceData(loanBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the loan book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        replaceData(loanBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has loan book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has loan book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < loanBookStateList.size() - 1;
    }

    /**
     * Checks if this Versioned Loan Book is equal to the specified Versioned Loan Book, but
     * only compares the editable fields of the Loans.
     *
     * @see Loan#hasEqualEditableFields(Loan)
     */
    public boolean hasEqualEditableFields(VersionedLoanBook other) {
        if (other == this) {
            return true;
        }

        return super.hasEqualEditableFields(other)
                && CollectionUtil.testByElement(loanBookStateList, other.loanBookStateList,
                        ReadOnlyLoanBook::hasEqualEditableFields)
                && currentStatePointer == other.currentStatePointer;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedLoanBook)) {
            return false;
        }

        VersionedLoanBook otherVersionedLoanBook = (VersionedLoanBook) other;

        // state check
        return super.equals(otherVersionedLoanBook)
                && loanBookStateList.equals(otherVersionedLoanBook.loanBookStateList)
                && currentStatePointer == otherVersionedLoanBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of loanBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of loanBookState list, unable to redo.");
        }
    }
}

package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Loan> PREDICATE_SHOW_ALL_LOANS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the address book.
     */
    boolean hasLoan(Loan loan);

    /**
     * Deletes the given loan.
     * The loan must exist in the address book.
     */
    void deleteLoan(Loan target);

    /**
     * Adds the given loan.
     * {@code loan} must not already exist in the address book.
     */
    void addLoan(Loan loan);

    /**
     * Replaces the given loan {@code target} with {@code editedLoan}.
     * {@code target} must exist in the address book.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the address book.
     */
    void updateLoan(Loan target, Loan editedLoan);

    /** Returns an unmodifiable view of the filtered loan list */
    ObservableList<Loan> getFilteredLoanList();

    /**
     * Updates the filter of the filtered loan list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLoanList(Predicate<Loan> predicate);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();
}

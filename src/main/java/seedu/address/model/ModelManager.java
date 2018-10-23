package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.LoanBookChangedEvent;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * Represents the in-memory model of the loan book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedLoanBook versionedLoanBook;
    private final FilteredList<Bike> filteredBikes;
    private final FilteredList<Loan> filteredLoans;
    private final UserPrefs preference;

    /**
     * Initializes a ModelManager with the given loanBook and userPrefs.
     */
    public ModelManager(ReadOnlyLoanBook loanBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(loanBook, userPrefs);

        logger.fine("Initializing with loan book: " + loanBook + " and user prefs " + userPrefs);

        versionedLoanBook = new VersionedLoanBook(loanBook);
        filteredBikes = new FilteredList<>(versionedLoanBook.getBikeList());
        filteredLoans = new FilteredList<>(versionedLoanBook.getLoanList());
        preference = userPrefs;
    }

    public ModelManager() {
        this(new LoanBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyLoanBook newData) {
        versionedLoanBook.resetData(newData);
        indicateLoanBookChanged();
    }

    @Override
    public ReadOnlyLoanBook getLoanBook() {
        return versionedLoanBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateLoanBookChanged() {
        raise(new LoanBookChangedEvent(versionedLoanBook));
    }

    //=========== Bike List Mutators =============================================================

    @Override
    public boolean hasBike(Bike bike) {
        requireNonNull(bike);
        return versionedLoanBook.hasBike(bike);
    }

    @Override
    public void addBike(Bike bike) {
        versionedLoanBook.addBike(bike);
        indicateLoanBookChanged();
    }

    @Override
    public void deleteBike(Bike target) {
        versionedLoanBook.removeBike(target);
        indicateLoanBookChanged();
    }

    @Override
    public void updateBike(Bike target, Bike editedBike) {
        requireAllNonNull(target, editedBike);

        versionedLoanBook.updateBike(target, editedBike);
        indicateLoanBookChanged();
    }

    //=========== Filtered Bike List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Bike} backed by the internal list of
     * {@code versionedLoanBook}
     */
    @Override
    public ObservableList<Bike> getFilteredBikeList() {
        return FXCollections.unmodifiableObservableList(filteredBikes);
    }

    @Override
    public void updateFilteredBikeList(Predicate<Bike> predicate) {
        requireNonNull(predicate);
        filteredBikes.setPredicate(predicate);
    }

    //=========== Loan List Mutators =============================================================

    @Override
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return versionedLoanBook.hasLoan(loan);
    }

    @Override
    public void addLoan(Loan loan) {
        versionedLoanBook.addLoan(loan);
        updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);
        indicateLoanBookChanged();
    }

    @Override
    public void deleteLoan(Loan target) {
        versionedLoanBook.removeLoan(target);
        indicateLoanBookChanged();
    }

    @Override
    public void setPass(Password pass) {
        preference.setPass(pass);
    }

    @Override
    public String getPass() {
        return preference.getPass();
    }

    @Override
    public void updateLoan(Loan target, Loan editedLoan) {
        requireAllNonNull(target, editedLoan);

        versionedLoanBook.updateLoan(target, editedLoan);
        indicateLoanBookChanged();
    }

    //=========== Filtered Loan List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Loan} backed by the internal list of
     * {@code versionedLoanBook}
     */
    @Override
    public ObservableList<Loan> getFilteredLoanList() {
        return FXCollections.unmodifiableObservableList(filteredLoans);
    }

    @Override
    public void updateFilteredLoanList(Predicate<Loan> predicate) {
        requireNonNull(predicate);
        filteredLoans.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoLoanBook() {
        return versionedLoanBook.canUndo();
    }

    @Override
    public boolean canRedoLoanBook() {
        return versionedLoanBook.canRedo();
    }

    @Override
    public void undoLoanBook() {
        versionedLoanBook.undo();
        indicateLoanBookChanged();
    }

    @Override
    public void redoLoanBook() {
        versionedLoanBook.redo();
        indicateLoanBookChanged();
    }

    @Override
    public void commitLoanBook() {
        versionedLoanBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedLoanBook.equals(other.versionedLoanBook)
                && filteredBikes.equals(other.filteredBikes)
                && filteredLoans.equals(other.filteredLoans);
    }

}

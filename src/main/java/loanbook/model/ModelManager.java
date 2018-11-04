package loanbook.model;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.CollectionUtil.requireAllNonNull;
import static loanbook.commons.util.CollectionUtil.testByElement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import loanbook.commons.core.ComponentManager;
import loanbook.commons.core.LogsCenter;
import loanbook.commons.events.model.LoanBookChangedEvent;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;

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
    public void replaceData(ReadOnlyLoanBook newData) {
        versionedLoanBook.replaceData(newData);
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

    //=========== Bike List Accessors and Mutators =============================================================

    @Override
    public boolean hasBike(Bike bike) {
        requireNonNull(bike);
        return versionedLoanBook.hasBike(bike);
    }

    @Override
    public Optional<Bike> getBike(String bikeName) {
        return versionedLoanBook.getBike(bikeName);
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

    @Override
    public void setBikes(List<Bike> bikes) {
        versionedLoanBook.setBikes(bikes);
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

    //=========== Loan List Accessors and Mutators =============================================================

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
    public void setLoans(List<Loan> loans) {
        versionedLoanBook.setLoans(loans);
        indicateLoanBookChanged();
    }

    /**
     * Clears the loan list and resets the loan ID.
     */
    @Override
    public void resetLoans() {
        setLoans(Collections.emptyList());
        resetId();
        // Change has already been indicated in the above commands
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

    //=========== Loan ID Methods ===========================================================================

    @Override
    public LoanId getNextAvailableId() {
        LoanId output = versionedLoanBook.getNextAvailableLoanId();
        indicateLoanBookChanged();

        return output;
    }

    @Override
    public boolean hasNextAvailableId() {
        return versionedLoanBook.hasNextAvailableLoanId();
    }

    @Override

    public void resetId() {
        versionedLoanBook.resetId();
        indicateLoanBookChanged();
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

    //=========== Password =================================================================================

    @Override
    public void setPass(Password pass) {
        preference.setPass(pass);
    }

    @Override
    public String getPass() {
        return preference.getPass();
    }

    //=========== Utility ==================================================================================

    @Override
    public boolean hasEqualEditableFields(Model other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager otherManager = (ModelManager) other;
        return versionedLoanBook.hasEqualEditableFields(otherManager.versionedLoanBook)
                && filteredBikes.equals(otherManager.filteredBikes)
                && testByElement(filteredLoans, otherManager.filteredLoans, Loan::hasEqualEditableFields);
    }

    //=========== Email =================================================================================

    @Override
    public void setMyEmail(String email) {
        preference.setDefaultEmail(email);
    }

    @Override
    public String getMyEmail() {
        return preference.getDefaultEmail();
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

    @Override
    public String toString() {
        return logger
            + ", " + versionedLoanBook
            + ", " + filteredBikes
            + ", " + filteredLoans
            + ", " + preference;

    }

}

package seedu.address.model;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.address.model.bike.Bike;
import seedu.address.model.bike.UniqueBikeList;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanIdManager;
import seedu.address.model.loan.UniqueLoanList;

/**
 * Wraps all data (bikes and loans) at the loanbook level.
 * Duplicates are not allowed.
 */
public class LoanBook implements ReadOnlyLoanBook {

    private final UniqueBikeList bikes;
    private final UniqueLoanList loans;
    private final LoanIdManager loanIdManager;

    /**
     * Default constructor.
     */
    public LoanBook() {
        bikes = new UniqueBikeList();
        loans = new UniqueLoanList();
        loanIdManager = new LoanIdManager();
    }

    /**
     * Creates an LoanBook using the Bikes and Loans in the {@code toBeCopied}
     */
    public LoanBook(ReadOnlyLoanBook toBeCopied) {
        this();
        replaceData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the bike list with {@code bikes}.
     * {@code bikes} must not contain duplicate bikes.
     */
    public void setBikes(List<Bike> bikes) {
        this.bikes.setAll(bikes);
    }

    /**
     * Replaces the contents of the loan list with {@code loans}.
     * {@code loans} must not contain duplicate loans.
     */
    public void setLoans(List<Loan> loans) {
        this.loans.setAll(loans);
    }

    /**
     * Replaces the state of this LoanBooks's Loan ID Manager with the specified manager.
     */
    public void setLoanIdManager(LoanIdManager loanIdManager) {
        this.loanIdManager.setFromExistingManager(loanIdManager);
    }

    /**
     * Replaces the existing data of this {@code LoanBook} with {@code newData}.
     */
    public void replaceData(ReadOnlyLoanBook newData) {
        requireNonNull(newData);

        setBikes(newData.getBikeList());
        setLoans(newData.getLoanList());
        setLoanIdManager(newData.getLoanIdManager());
    }

    //// bike-level operations

    /**
     * Returns true if a bike with the same identity as {@code bike} exists in the loan book.
     */
    public boolean hasBike(Bike bike) {
        requireNonNull(bike);
        return bikes.contains(bike);
    }

    /**
     * Returns a bike in the list whose name matches {@code bikeName}.
     */
    public Optional<Bike> getBike(String bikeName) {
        return bikes.getBike(bikeName);
    }

    /**
     * Adds a bike to the loan book.
     * The bike must not already exist in the loan book.
     */
    public void addBike(Bike p) {
        bikes.add(p);
    }

    /**
     * Replaces the given bike {@code target} in the list with {@code editedBike}.
     * {@code target} must exist in the loan book.
     * The bike identity of {@code editedBike} must not be the same as another existing bike in the loan book.
     */
    public void updateBike(Bike target, Bike editedBike) {
        requireNonNull(editedBike);

        bikes.set(target, editedBike);
    }

    /**
     * Removes {@code key} from this {@code LoanBook}.
     * {@code key} must exist in the loan book.
     */
    public void removeBike(Bike key) {
        bikes.remove(key);
    }

    //// loan-level operations

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the loan book.
     */
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return loans.contains(loan);
    }

    /**
     * Adds a loan to the loan book.
     * The loan must not already exist in the loan book.
     */
    public void addLoan(Loan p) {
        loans.add(p);
    }

    /**
     * Replaces the given loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the loan book.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the loan book.
     */
    public void updateLoan(Loan target, Loan editedLoan) {
        requireNonNull(editedLoan);

        loans.set(target, editedLoan);
    }

    /**
     * Removes {@code key} from this {@code LoanBook}.
     * {@code key} must exist in the loan book.
     */
    public void removeLoan(Loan key) {
        loans.remove(key);
    }

    //// Loan ID methods

    /**
     * Gets the next available Loan ID.
     */
    public LoanId getNextAvailableLoanId() {
        return loanIdManager.getNextAvailableLoanId();
    }

    /**
     * Checks if there is a next available Loan ID.
     *
     * @return true if there exists a next available loan ID.
     */
    public boolean hasNextAvailableLoanId() {
        return loanIdManager.hasNextAvailableLoanId();
    }

    /**
     * Resets the Loan ID Manager.
     */
    public void resetId() {
        this.loanIdManager.reset();
    }

    //// util methods

    @Override
    public String toString() {
        return loans.asUnmodifiableObservableList().size() + " loans, "
             + bikes.asUnmodifiableObservableList().size() + " bikes, "
             + "LoanIdManager: " + loanIdManager.toString();
        // TODO: refine later
    }

    @Override
    public ObservableList<Bike> getBikeList() {
        return bikes.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Loan> getLoanList() {
        return loans.asUnmodifiableObservableList();
    }

    @Override
    public LoanIdManager getLoanIdManager() {
        return new LoanIdManager(loanIdManager.getLastUsedLoanId());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }
        if (!(other instanceof LoanBook)) { // instanceof handles nulls
            return false;
        }
        LoanBook otherLoanBook = (LoanBook) other;

        return loans.equals(otherLoanBook.loans)
                && bikes.equals(otherLoanBook.bikes)
                && loanIdManager.equals(otherLoanBook.loanIdManager);
    }

    @Override
    public int hashCode() {
        // Use Objects.hash()
        return hash(bikes, loans, loanIdManager);
    }
}

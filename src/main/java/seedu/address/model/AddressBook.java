package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.UniqueLoanList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameLoan comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueLoanList loans;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        loans = new UniqueLoanList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Loans in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the loan list with {@code loans}.
     * {@code loans} must not contain duplicate loans.
     */
    public void setLoans(List<Loan> loans) {
        this.loans.setLoans(loans);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setLoans(newData.getLoanList());
    }

    //// loan-level operations

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the address book.
     */
    public boolean hasLoan(Loan loan) {
        requireNonNull(loan);
        return loans.contains(loan);
    }

    /**
     * Adds a loan to the address book.
     * The loan must not already exist in the address book.
     */
    public void addLoan(Loan p) {
        loans.add(p);
    }

    /**
     * Replaces the given loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the address book.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the address book.
     */
    public void updateLoan(Loan target, Loan editedLoan) {
        requireNonNull(editedLoan);

        loans.setLoan(target, editedLoan);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeLoan(Loan key) {
        loans.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return loans.asUnmodifiableObservableList().size() + " loans";
        // TODO: refine later
    }

    @Override
    public ObservableList<Loan> getLoanList() {
        return loans.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && loans.equals(((AddressBook) other).loans));
    }

    @Override
    public int hashCode() {
        return loans.hashCode();
    }
}

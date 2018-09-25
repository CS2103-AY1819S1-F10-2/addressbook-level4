package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
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
    public void setPersons(List<Loan> loans) {
        this.persons.setPersons(loans);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// loan-level operations

    /**
     * Returns true if a loan with the same identity as {@code loan} exists in the address book.
     */
    public boolean hasPerson(Loan loan) {
        requireNonNull(loan);
        return persons.contains(loan);
    }

    /**
     * Adds a loan to the address book.
     * The loan must not already exist in the address book.
     */
    public void addPerson(Loan p) {
        persons.add(p);
    }

    /**
     * Replaces the given loan {@code target} in the list with {@code editedLoan}.
     * {@code target} must exist in the address book.
     * The loan identity of {@code editedLoan} must not be the same as another existing loan in the address book.
     */
    public void updatePerson(Loan target, Loan editedLoan) {
        requireNonNull(editedLoan);

        persons.setPerson(target, editedLoan);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Loan key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Loan> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}

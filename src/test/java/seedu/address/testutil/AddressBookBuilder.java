package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.loan.Loan;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withLoan("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Loan} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLoan(Loan loan) {
        addressBook.addLoan(loan);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}

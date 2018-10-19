package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "addressbook")
public class XmlSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_BIKE = "Bikes list contains duplicate bike(s).";
    public static final String MESSAGE_DUPLICATE_LOAN = "Loans list contains duplicate loan(s).";

    @XmlElement
    private List<XmlAdaptedBike> bikes;
    @XmlElement
    private List<XmlAdaptedLoan> loans;

    /**
     * Creates an empty XmlSerializableAddressBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableAddressBook() {
        bikes = new ArrayList<>();
        loans = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableAddressBook(ReadOnlyAddressBook src) {
        this();
        bikes.addAll(src.getBikeList().stream().map(XmlAdaptedBike::new).collect(Collectors.toList()));
        loans.addAll(src.getLoanList().stream().map(XmlAdaptedLoan::new).collect(Collectors.toList()));
    }

    /**
     * Converts this addressbook into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedBike} or {@code XmlAdaptedLoan}.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (XmlAdaptedBike p : bikes) {
            Bike bike = p.toModelType();
            if (addressBook.hasBike(bike)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BIKE);
            }
            addressBook.addBike(bike);
        }
        for (XmlAdaptedLoan p : loans) {
            Loan loan = p.toModelType();
            if (addressBook.hasLoan(loan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOAN);
            }
            addressBook.addLoan(loan);
        }
        return addressBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableAddressBook)) {
            return false;
        }
        return bikes.equals(((XmlSerializableAddressBook) other).bikes)
            && loans.equals(((XmlSerializableAddressBook) other).loans);
    }
}

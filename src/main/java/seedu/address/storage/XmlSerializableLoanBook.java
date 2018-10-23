package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * An Immutable LoanBook that is serializable to XML format
 */
@XmlRootElement(name = "loanbook")
public class XmlSerializableLoanBook {

    public static final String MESSAGE_DUPLICATE_BIKE = "Bikes list contains duplicate bike(s).";
    public static final String MESSAGE_DUPLICATE_LOAN = "Loans list contains duplicate loan(s).";

    @XmlElement
    private List<XmlAdaptedBike> bikes;
    @XmlElement
    private List<XmlAdaptedLoan> loans;

    /**
     * Creates an empty XmlSerializableLoanBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLoanBook() {
        bikes = new ArrayList<>();
        loans = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableLoanBook(ReadOnlyLoanBook src) {
        this();
        bikes.addAll(src.getBikeList().stream().map(XmlAdaptedBike::new).collect(Collectors.toList()));
        loans.addAll(src.getLoanList().stream().map(XmlAdaptedLoan::new).collect(Collectors.toList()));
    }

    /**
     * Converts this loanbook into the model's {@code LoanBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedBike} or {@code XmlAdaptedLoan}.
     */
    public LoanBook toModelType() throws IllegalValueException {
        LoanBook loanBook = new LoanBook();
        for (XmlAdaptedBike p : bikes) {
            Bike bike = p.toModelType();
            if (loanBook.hasBike(bike)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BIKE);
            }
            loanBook.addBike(bike);
        }
        for (XmlAdaptedLoan p : loans) {
            Loan loan = p.toModelType();
            if (loanBook.hasLoan(loan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LOAN);
            }
            loanBook.addLoan(loan);
        }
        return loanBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlSerializableLoanBook)) {
            return false;
        }

        return bikes.equals(((XmlSerializableLoanBook) other).bikes)
            && loans.equals(((XmlSerializableLoanBook) other).loans);
    }
}

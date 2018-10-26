package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.loan.LoanId;

/**
 * JAXB-friendly adapted version of the LoanId.
 */
public class XmlAdaptedLoanId {

    @XmlValue
    private int loanId;

    /**
     * Constructs an XmlAdaptedLoanId.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLoanId() {}

    /**
     * Constructs an {@code XmlAdaptedLoanId} with the given loan ID as a String.
     *
     * @param loanId The specified Loan ID as a string.
     */
    public XmlAdaptedLoanId(int loanId) {
        this.loanId = loanId;
    }

    public XmlAdaptedLoanId(LoanId source) {
        loanId = source.value;
    }

    /**
     * Converts this JAXB-friendly adapted Loan ID object into the model's LoanId object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Loan ID.
     */
    public LoanId toModelType() throws IllegalValueException {
        if (!LoanId.isValidLoanId(loanId)) {
            throw new IllegalValueException(LoanId.MESSAGE_LOANID_CONSTRAINTS);
        }

        return LoanId.fromInt(loanId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLoanId)) {
            return false;
        }

        return loanId == (((XmlAdaptedLoanId) other).loanId);
    }
}

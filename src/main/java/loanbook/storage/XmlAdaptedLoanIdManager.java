package loanbook.storage;

import javax.xml.bind.annotation.XmlElement;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanIdManager;

/**
 * JAXB-friendly adapted version of the LoanIdManager.
 */
public class XmlAdaptedLoanIdManager {

    @XmlElement
    private XmlAdaptedLoanId lastUsedLoanId;

    /**
     * Constructs an XmlAdaptedLoanIdManager.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedLoanIdManager() {}

    /**
     * Constructs an {@code XmlAdaptedLoanIdManager} with the given last used Loan ID.
     */
    public XmlAdaptedLoanIdManager(XmlAdaptedLoanId lastUsedLoanId) {
        this.lastUsedLoanId = lastUsedLoanId;
    }

    /**
     * Constructs an {@code XmlAdaptedLoanIdManager} from the given Loan ID Manager for JAXB use.
     * Future changes to the specified LoanIdManager will not affect this instance.
     */
    public XmlAdaptedLoanIdManager(LoanIdManager source) {
        LoanId lastUsedLoanIdModel = source.getLastUsedLoanId();
        if (lastUsedLoanIdModel == null) {
            // If the source LoanIdManager has no last used Loan ID,
            // this instance will also have no last used Loan ID.
            lastUsedLoanId = null;
        } else {
            // Otherwise, convert the last used Loan ID into an
            // XmlAdaptedLoanId object.
            lastUsedLoanId = new XmlAdaptedLoanId(lastUsedLoanIdModel);
        }
    }

    /**
     * Converts this JAXB-friendly adapted Loan ID Manager object into the model's LoanIdManager object.
     *
     * @throws IllegalValueException if there were any data constraints violated during the conversion
     */
    public LoanIdManager toModelType() throws IllegalValueException {
        if (lastUsedLoanId == null) {
            return new LoanIdManager();
        } else {
            return new LoanIdManager(lastUsedLoanId.toModelType());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedLoanIdManager)) {
            return false;
        }

        if (lastUsedLoanId == null) {
            return ((XmlAdaptedLoanIdManager) other).lastUsedLoanId == null;
        } else {
            return lastUsedLoanId.equals(((XmlAdaptedLoanIdManager) other).lastUsedLoanId);
        }
    }
}

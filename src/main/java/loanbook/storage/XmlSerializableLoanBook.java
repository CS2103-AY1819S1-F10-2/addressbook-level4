package loanbook.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.LoanBook;
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanIdManager;

/**
 * An Immutable LoanBook that is serializable to XML format
 */
@XmlRootElement(name = "loanbook")
public class XmlSerializableLoanBook {

    public static final String MESSAGE_DUPLICATE_BIKE = "Bikes list contains duplicate bike(s).";
    public static final String MESSAGE_DUPLICATE_LOAN_ID = "Loans list contains duplicate Loan ID(s).";
    public static final String MESSAGE_ILLEGAL_LOAN_ID_MANAGER = "Loan ID Manager is in an illegal state.";

    @XmlElement
    private List<XmlAdaptedBike> bikes;
    @XmlElement
    private List<XmlAdaptedLoan> loans;
    @XmlElement(required = true)
    private XmlAdaptedLoanIdManager loanIdManager;

    /**
     * Creates an empty XmlSerializableLoanBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableLoanBook() {
        bikes = new ArrayList<>();
        loans = new ArrayList<>();
        loanIdManager = new XmlAdaptedLoanIdManager();
    }

    /**
     * Conversion
     */
    public XmlSerializableLoanBook(ReadOnlyLoanBook src) {
        this();
        bikes.addAll(src.getBikeList().stream().map(XmlAdaptedBike::new).collect(Collectors.toList()));
        loans.addAll(src.getLoanList().stream().map(XmlAdaptedLoan::new).collect(Collectors.toList()));
        loanIdManager = new XmlAdaptedLoanIdManager(src.getLoanIdManager());
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

        ArrayList<Loan> modelLoans = new ArrayList<>();
        for (XmlAdaptedLoan p : loans) {
            modelLoans.add(p.toModelType());
        }

        LoanIdManager modelIdManager = loanIdManager.toModelType();

        if (hasDuplicateLoanIds(modelLoans)) {
            throw new IllegalValueException(MESSAGE_DUPLICATE_LOAN_ID);
        }

        if (!isLoanIdManagerInLegalState(modelIdManager, modelLoans)) {
            throw new IllegalValueException(MESSAGE_ILLEGAL_LOAN_ID_MANAGER);
        }

        for (Loan modelLoan : modelLoans) {
            loanBook.addLoan(modelLoan);
        }
        loanBook.setLoanIdManager(modelIdManager);

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

        XmlSerializableLoanBook otherXmlLoanBook = (XmlSerializableLoanBook) other;
        return bikes.equals(otherXmlLoanBook.bikes)
            && loans.equals(otherXmlLoanBook.loans)
            && loanIdManager.equals(otherXmlLoanBook.loanIdManager);
    }

    /**
     * Checks if the specified list of Loans has a duplicate Loan ID.
     */
    private static boolean hasDuplicateLoanIds(List<Loan> loans) {
        HashSet<LoanId> loanIdHashSet = new HashSet<>();
        for (Loan loan : loans) {
            LoanId loanId = loan.getLoanId();

            if (loanIdHashSet.contains(loanId)) {
                return true;
            }

            loanIdHashSet.add(loanId);
        }

        return false;
    }

    /**
     * Checks if the specified Loan ID Manager is in a legal state given the specified list of Loans.
     */
    private static boolean isLoanIdManagerInLegalState(LoanIdManager loanIdManager, List<Loan> loanList) {
        // Here, the ID Manager is deemed illegal if the "Last used Loan ID" field
        // in the ID Manager is smaller than the maximum Loan ID in the loan list.

        LoanId maximumObservedLoanId = null;
        for (Loan loan : loanList) {
            LoanId loanId = loan.getLoanId();
            if (maximumObservedLoanId == null || loanId.value > maximumObservedLoanId.value) {
                maximumObservedLoanId = loanId;
            }
        }

        // If the list of Loans is empty, then the ID Manager is definitely legal.
        if (maximumObservedLoanId == null) {
            return true;
        }

        // If the list of Loans is not empty, but the ID Manager has no last used ID, then the ID Manager is illegal.
        LoanId managerLastUsedId = loanIdManager.getLastUsedLoanId();
        if (managerLastUsedId == null) {
            return false;
        }

        return managerLastUsedId.value >= maximumObservedLoanId.value;
    }
}

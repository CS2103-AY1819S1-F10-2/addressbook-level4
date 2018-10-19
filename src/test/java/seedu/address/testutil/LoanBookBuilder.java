package seedu.address.testutil;

import seedu.address.model.LoanBook;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code LoanBook ab = new LoanBookBuilder().withBike("BiK001").withLoan("John", "Doe").build();}
 */
public class LoanBookBuilder {

    private LoanBook loanBook;

    public LoanBookBuilder() {
        loanBook = new LoanBook();
    }

    public LoanBookBuilder(LoanBook loanBook) {
        this.loanBook = loanBook;
    }

    /**
     * Adds a new {@code Bike} to the {@code LoanBook} that we are building.
     */
    public LoanBookBuilder withBike(Bike bike) {
        loanBook.addBike(bike);
        return this;
    }

    /**
     * Adds a new {@code Loan} to the {@code LoanBook} that we are building.
     */
    public LoanBookBuilder withLoan(Loan loan) {
        loanBook.addLoan(loan);
        return this;
    }

    public LoanBook build() {
        return loanBook;
    }
}

package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalBikes.BIKE1;
import static seedu.address.testutil.TypicalLoanBook.getTypicalLoanBook;
import static seedu.address.testutil.TypicalLoans.ALICE;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.exceptions.DuplicateLoanException;
import seedu.address.testutil.LoanBuilder;

public class LoanBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LoanBook loanBook = new LoanBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), loanBook.getLoanList());
        assertEquals(Collections.emptyList(), loanBook.getBikeList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loanBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLoanBook_replacesData() {
        LoanBook newData = getTypicalLoanBook();
        loanBook.resetData(newData);
        assertEquals(newData, loanBook);
    }

    @Test
    public void resetData_withDuplicateLoans_throwsDuplicateLoanException() {
        // Two loans with the same identity fields
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bike> newBikes = Arrays.asList();
        List<Loan> newLoans = Arrays.asList(ALICE, editedAlice);
        LoanBookStub newData = new LoanBookStub(newBikes, newLoans);

        thrown.expect(DuplicateLoanException.class);
        loanBook.resetData(newData);
    }

    @Test
    public void hasBike_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loanBook.hasBike(null);
    }

    @Test
    public void hasLoan_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loanBook.hasLoan(null);
    }

    @Test
    public void hasBike_bikeNotInLoanBook_returnsFalse() {
        assertFalse(loanBook.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanNotInLoanBook_returnsFalse() {
        assertFalse(loanBook.hasLoan(ALICE));
    }

    @Test
    public void hasBike_bikeInLoanBook_returnsTrue() {
        loanBook.addBike(BIKE1);
        assertTrue(loanBook.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanInLoanBook_returnsTrue() {
        loanBook.addLoan(ALICE);
        assertTrue(loanBook.hasLoan(ALICE));
    }

    @Test
    public void hasLoan_loanWithSameIdentityFieldsInLoanBook_returnsTrue() {
        loanBook.addLoan(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(loanBook.hasLoan(editedAlice));
    }

    @Test
    public void getBikeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        loanBook.getBikeList().remove(0);
    }

    @Test
    public void getLoanList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        loanBook.getLoanList().remove(0);
    }

    /**
     * A stub ReadOnlyLoanBook whose loans list can violate interface constraints.
     */
    private static class LoanBookStub implements ReadOnlyLoanBook {
        private final ObservableList<Bike> bikes = FXCollections.observableArrayList();
        private final ObservableList<Loan> loans = FXCollections.observableArrayList();

        LoanBookStub(Collection<Bike> bikes, Collection<Loan> loans) {
            this.bikes.setAll(bikes);
            this.loans.setAll(loans);
        }

        @Override
        public ObservableList<Bike> getBikeList() {
            return bikes;
        }

        @Override
        public ObservableList<Loan> getLoanList() {
            return loans;
        }
    }

}

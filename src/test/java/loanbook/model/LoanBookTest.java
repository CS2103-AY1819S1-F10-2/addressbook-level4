package loanbook.model;

import static loanbook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static loanbook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static loanbook.testutil.TypicalBikes.BIKE1;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static loanbook.testutil.TypicalLoans.ALICE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loanbook.model.bike.Bike;
import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.LoanIdManager;
import loanbook.model.loan.exceptions.DuplicateLoanException;
import loanbook.testutil.LoanBuilder;

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
    public void replaceData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        loanBook.replaceData(null);
    }

    @Test
    public void replaceData_withValidReadOnlyLoanBook_replacesData() {
        LoanBook newData = getTypicalLoanBook();
        loanBook.replaceData(newData);
        assertEquals(newData, loanBook);
    }

    @Test
    public void replaceData_withDuplicateLoans_throwsDuplicateLoanException() {
        // Two loans with the same identity fields
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Bike> newBikes = Arrays.asList();
        List<Loan> newLoans = Arrays.asList(ALICE, editedAlice);
        LoanBookStub newData = new LoanBookStub(newBikes, newLoans);

        thrown.expect(DuplicateLoanException.class);
        loanBook.replaceData(newData);
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

    @Test
    public void getNextAvailableLoanId_fromDefault_success() {
        // Test from default Loan ID Manager (i.e. from 0)
        for (int i = 0; i < 10; ++i) {
            LoanId expectedId = LoanId.fromInt(i);
            LoanId actualId = loanBook.getNextAvailableLoanId();

            assertEquals(expectedId, actualId);
        }
    }

    @Test
    public void getNextAvailableLoanId_fromExistingManager_success() {
        // Test from existing manager starting at 7528
        int lastUsedLoanIdValue = 7528;
        LoanIdManager existingManager = new LoanIdManager(LoanId.fromInt(lastUsedLoanIdValue));

        loanBook.setLoanIdManager(existingManager);
        for (int i = 1; i <= 10; ++i) {
            LoanId expectedId = LoanId.fromInt(lastUsedLoanIdValue + i);
            LoanId actualId = loanBook.getNextAvailableLoanId();

            assertEquals(expectedId, actualId);
        }
    }

    @Test
    public void hasNextAvailableLoanId_fromMaximumLoanId_returnsFalse() {
        LoanId expectedMaximumId = LoanId.fromInt(999999999);
        assertTrue(expectedMaximumId.isMaximumId());

        LoanIdManager existingManager = new LoanIdManager(expectedMaximumId);
        loanBook.setLoanIdManager(existingManager);

        assertFalse(loanBook.hasNextAvailableLoanId());
    }

    /**
     * A stub ReadOnlyLoanBook whose loans list can violate interface constraints.
     */
    private static class LoanBookStub implements ReadOnlyLoanBook {
        private final ObservableList<Bike> bikes = FXCollections.observableArrayList();
        private final ObservableList<Loan> loans = FXCollections.observableArrayList();
        private final LoanIdManager loanIdManager = new LoanIdManager();

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

        @Override
        public LoanIdManager getLoanIdManager() {
            return loanIdManager;
        }
    }

}

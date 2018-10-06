package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalLoans.ALICE;
import static seedu.address.testutil.TypicalLoans.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.loan.exceptions.DuplicateLoanException;
import seedu.address.model.loan.exceptions.LoanNotFoundException;
import seedu.address.testutil.LoanBuilder;

public class UniqueLoanListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueLoanList uniqueLoanList = new UniqueLoanList();

    @Test
    public void containsNullLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.contains(null);
    }

    @Test
    public void containsLoanNotInListReturnsFalse() {
        assertFalse(uniqueLoanList.contains(ALICE));
    }

    @Test
    public void containsLoanInListReturnsTrue() {
        uniqueLoanList.add(ALICE);
        assertTrue(uniqueLoanList.contains(ALICE));
    }

    @Test
    public void containsLoanWithSameIdentityFieldsInListReturnsTrue() {
        uniqueLoanList.add(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueLoanList.contains(editedAlice));
    }

    @Test
    public void addNullLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.add(null);
    }

    @Test
    public void addDuplicateLoanThrowsDuplicateLoanException() {
        uniqueLoanList.add(ALICE);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.add(ALICE);
    }

    @Test
    public void setLoanNullTargetLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoan(null, ALICE);
    }

    @Test
    public void setLoanNullEditedLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoan(ALICE, null);
    }

    @Test
    public void setLoanTargetLoanNotInListThrowsLoanNotFoundException() {
        thrown.expect(LoanNotFoundException.class);
        uniqueLoanList.setLoan(ALICE, ALICE);
    }

    @Test
    public void setLoanEditedLoanIsSameLoan_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.setLoan(ALICE, ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(ALICE);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoanEditedLoanHasSameIdentity_success() {
        uniqueLoanList.add(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueLoanList.setLoan(ALICE, editedAlice);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(editedAlice);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoanEditedLoanHasDifferentIdentity_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.setLoan(ALICE, BOB);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoanEditedLoanHasNonUniqueIdentityThrowsDuplicateLoanException() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.add(BOB);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.setLoan(ALICE, BOB);
    }

    @Test
    public void removeNullLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.remove(null);
    }

    @Test
    public void removeLoanDoesNotExistThrowsLoanNotFoundException() {
        thrown.expect(LoanNotFoundException.class);
        uniqueLoanList.remove(ALICE);
    }

    @Test
    public void remove_existingLoanRemovesLoan() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.remove(ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoansNullUniqueLoanListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoans((UniqueLoanList) null);
    }

    @Test
    public void setLoansUniqueLoanListReplacesOwnListWithProvidedUniqueLoanList() {
        uniqueLoanList.add(ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        uniqueLoanList.setLoans(expectedUniqueLoanList);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoansNullListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoans((List<Loan>) null);
    }

    @Test
    public void setLoansListReplacesOwnListWithProvidedList() {
        uniqueLoanList.add(ALICE);
        List<Loan> loanList = Collections.singletonList(BOB);
        uniqueLoanList.setLoans(loanList);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoansListWithDuplicateLoansThrowsDuplicateLoanException() {
        List<Loan> listWithDuplicateLoans = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.setLoans(listWithDuplicateLoans);
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueLoanList.asUnmodifiableObservableList().remove(0);
    }
}

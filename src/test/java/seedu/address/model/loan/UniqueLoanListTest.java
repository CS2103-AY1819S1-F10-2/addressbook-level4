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
    public void setNullTargetLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.set(null, ALICE);
    }

    @Test
    public void setNullEditedLoanThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.set(ALICE, null);
    }

    @Test
    public void setTargetLoanNotInListThrowsLoanNotFoundException() {
        thrown.expect(LoanNotFoundException.class);
        uniqueLoanList.set(ALICE, ALICE);
    }

    @Test
    public void setEditedLoanIsSameLoan_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.set(ALICE, ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(ALICE);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setEditedLoanHasSameIdentity_success() {
        uniqueLoanList.add(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueLoanList.set(ALICE, editedAlice);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(editedAlice);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setEditedLoanHasDifferentIdentity_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.set(ALICE, BOB);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setEditedLoanHasNonUniqueIdentityThrowsDuplicateLoanException() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.add(BOB);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.set(ALICE, BOB);
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
    public void setNullUniqueLoanListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setAll((UniqueLoanList) null);
    }

    @Test
    public void setUniqueLoanListReplacesOwnListWithProvidedUniqueLoanList() {
        uniqueLoanList.add(ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        uniqueLoanList.setAll(expectedUniqueLoanList);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setNullListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setAll((List<Loan>) null);
    }

    @Test
    public void setListReplacesOwnListWithProvidedList() {
        uniqueLoanList.add(ALICE);
        List<Loan> loanList = Collections.singletonList(BOB);
        uniqueLoanList.setAll(loanList);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setListWithDuplicateLoansThrowsDuplicateLoanException() {
        List<Loan> listWithDuplicateLoans = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.setAll(listWithDuplicateLoans);
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueLoanList.asUnmodifiableObservableList().remove(0);
    }
}

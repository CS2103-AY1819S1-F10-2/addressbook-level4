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
    public void contains_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.contains(null);
    }

    @Test
    public void contains_loanNotInList_returnsFalse() {
        assertFalse(uniqueLoanList.contains(ALICE));
    }

    @Test
    public void contains_loanInList_returnsTrue() {
        uniqueLoanList.add(ALICE);
        assertTrue(uniqueLoanList.contains(ALICE));
    }

    @Test
    public void contains_loanWithSameIdentityFieldsInList_returnsTrue() {
        uniqueLoanList.add(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueLoanList.contains(editedAlice));
    }

    @Test
    public void add_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.add(null);
    }

    @Test
    public void add_duplicateLoan_throwsDuplicateLoanException() {
        uniqueLoanList.add(ALICE);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.add(ALICE);
    }

    @Test
    public void setLoan_nullTargetLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoan(null, ALICE);
    }

    @Test
    public void setLoan_nullEditedLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoan(ALICE, null);
    }

    @Test
    public void setLoan_targetLoanNotInList_throwsLoanNotFoundException() {
        thrown.expect(LoanNotFoundException.class);
        uniqueLoanList.setLoan(ALICE, ALICE);
    }

    @Test
    public void setLoan_editedLoanIsSameLoan_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.setLoan(ALICE, ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(ALICE);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoan_editedLoanHasSameIdentity_success() {
        uniqueLoanList.add(ALICE);
        Loan editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueLoanList.setLoan(ALICE, editedAlice);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(editedAlice);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoan_editedLoanHasDifferentIdentity_success() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.setLoan(ALICE, BOB);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoan_editedLoanHasNonUniqueIdentity_throwsDuplicateLoanException() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.add(BOB);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.setLoan(ALICE, BOB);
    }

    @Test
    public void remove_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.remove(null);
    }

    @Test
    public void remove_loanDoesNotExist_throwsLoanNotFoundException() {
        thrown.expect(LoanNotFoundException.class);
        uniqueLoanList.remove(ALICE);
    }

    @Test
    public void remove_existingLoan_removesLoan() {
        uniqueLoanList.add(ALICE);
        uniqueLoanList.remove(ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoans_nullUniqueLoanList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoans((UniqueLoanList) null);
    }

    @Test
    public void setLoans_uniqueLoanList_replacesOwnListWithProvidedUniqueLoanList() {
        uniqueLoanList.add(ALICE);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        uniqueLoanList.setLoans(expectedUniqueLoanList);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoans_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueLoanList.setLoans((List<Loan>) null);
    }

    @Test
    public void setLoans_list_replacesOwnListWithProvidedList() {
        uniqueLoanList.add(ALICE);
        List<Loan> loanList = Collections.singletonList(BOB);
        uniqueLoanList.setLoans(loanList);
        UniqueLoanList expectedUniqueLoanList = new UniqueLoanList();
        expectedUniqueLoanList.add(BOB);
        assertEquals(expectedUniqueLoanList, uniqueLoanList);
    }

    @Test
    public void setLoans_listWithDuplicateLoans_throwsDuplicateLoanException() {
        List<Loan> listWithDuplicateLoans = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateLoanException.class);
        uniqueLoanList.setLoans(listWithDuplicateLoans);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueLoanList.asUnmodifiableObservableList().remove(0);
    }
}

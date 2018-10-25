package seedu.address.model.loan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANRATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOANTIME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalLoans.ALICE;
import static seedu.address.testutil.TypicalLoans.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.LoanBuilder;

public class LoanTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Loan loan = new LoanBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        loan.getTags().remove(0);
    }

    @Test
    public void isSameLoan() {
        // same object -> returns true
        assertTrue(ALICE.isSame(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSame(null));

        // different name -> returns false
        Loan editedAlice = new LoanBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSame(editedAlice));

        // different nric -> returns false
        editedAlice = new LoanBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSame(editedAlice));

        // different bike -> returns false
        editedAlice = new LoanBuilder(ALICE).withBike(VALID_NAME_BIKE2).build();
        assertFalse(ALICE.isSame(editedAlice));

        // different phone, email, rate and time -> returns false
        editedAlice = new LoanBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withLoanRate(VALID_LOANRATE_BOB).withLoanTime(VALID_LOANTIME_BOB).build();
        assertFalse(ALICE.isSame(editedAlice));

        // same identity fields, same phone, different attributes -> returns true
        editedAlice = new LoanBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same identity fields, same email, different attributes -> returns true
        editedAlice = new LoanBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same identity fields, same phone, same email, different attributes -> returns true
        editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same identity fields, different rate -> returns true
        editedAlice = new LoanBuilder(ALICE).withLoanRate(VALID_LOANRATE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same identity fields, different time -> returns true
        editedAlice = new LoanBuilder(ALICE).withLoanTime(VALID_LOANTIME_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));

        // same identity fields, different rate and time -> returns true
        editedAlice = new LoanBuilder(ALICE).withLoanRate(VALID_LOANRATE_BOB).withLoanTime(VALID_LOANTIME_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSame(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Loan aliceCopy = new LoanBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different loan -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Loan editedAlice = new LoanBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new LoanBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new LoanBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new LoanBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new LoanBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void loanBuilderWithStatusConstructor() {
        Loan loan = new LoanBuilder().withLoanStatus("RETURNED").build();
        assertTrue(loan.getLoanStatus().equals(LoanStatus.RETURNED));
    }
}

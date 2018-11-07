package loanbook.model;

import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_BIKES;
import static loanbook.model.Model.PREDICATE_SHOW_ALL_LOANS;
import static loanbook.testutil.TypicalBikes.BIKE1;
import static loanbook.testutil.TypicalBikes.BIKE2;
import static loanbook.testutil.TypicalLoans.ALICE;
import static loanbook.testutil.TypicalLoans.BENSON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.model.loan.Loan;
import loanbook.model.loan.LoanId;
import loanbook.model.loan.NameContainsKeywordsPredicate;
import loanbook.testutil.LoanBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasBike_nullBike_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasBike(null);
    }

    @Test
    public void hasLoan_nullLoan_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasLoan(null);
    }

    @Test
    public void hasBike_bikeNotInLoanBook_returnsFalse() {
        assertFalse(modelManager.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanNotInLoanBook_returnsFalse() {
        assertFalse(modelManager.hasLoan(ALICE));
    }

    @Test
    public void hasBike_bikeInLoanBook_returnsTrue() {
        modelManager.addBike(BIKE1);
        assertTrue(modelManager.hasBike(BIKE1));
    }

    @Test
    public void hasLoan_loanInLoanBook_returnsTrue() {
        modelManager.addLoan(ALICE);
        assertTrue(modelManager.hasLoan(ALICE));
    }

    @Test
    public void getLoanById_loanExists_success() {
        modelManager.addLoan(ALICE);
        Optional<Loan> retrievedLoan = modelManager.getLoanById(ALICE.getLoanId());

        assertTrue(retrievedLoan.isPresent());
        assertEquals(retrievedLoan.get(), ALICE);
    }

    @Test
    public void getLoanById_loanDoesNotExist_returnEmptyOptional() {
        LoanId idExpectedNotToExist = LoanId.fromInt(999999999);
        Optional<Loan> retrievedLoan = modelManager.getLoanById(idExpectedNotToExist);

        assertFalse(retrievedLoan.isPresent());
    }

    @Test
    public void getBike_bikeNotInLoanBook_returnsEmpty() {
        assertEquals(Optional.empty(), modelManager.getBike(VALID_NAME_BIKE1));
    }

    @Test
    public void getBike_bikeInLoanBook_returnsBike() {
        modelManager.addBike(BIKE1);
        assertEquals(Optional.of(BIKE1), modelManager.getBike(VALID_NAME_BIKE1));
    }

    @Test
    public void getFilteredBikeList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredBikeList().remove(0);
    }

    @Test
    public void getFilteredLoanList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredLoanList().remove(0);
    }

    @Test
    public void getPass_returnsTrue() {
        String currPass = modelManager.getPass();
        assertTrue(Password.isSamePassword(currPass, new Password("a12345")));
    }

    @Test
    public void setPass_returnsTrue() {
        Password newPass = new Password("newpassword");
        modelManager.setPass(newPass);
        String currPass = modelManager.getPass();
        assertTrue(Password.isSamePassword(currPass, newPass));
    }

    @Test
    public void getAndSetMyEmail_returnsTrue() {
        UserPrefs prefs = new UserPrefs();
        prefs.setDefaultEmail(VALID_USER_EMAIL1);
        modelManager.setMyEmail(VALID_USER_EMAIL1);
        String userEmail = modelManager.getMyEmail();
        assertTrue(prefs.getDefaultEmail().equals(userEmail));
    }

    @Test
    public void equals() {
        LoanBook loanBook = new LoanBookBuilder()
                .withLoan(ALICE).withLoan(BENSON)
                .withBike(BIKE1).withBike(BIKE2).build();
        LoanBook differentLoanBook = new LoanBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(loanBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(loanBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager == null);

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different loanBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLoanBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().value.split("\\s+");
        modelManager.updateFilteredLoanList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)).forLoans());
        assertFalse(modelManager.equals(new ModelManager(loanBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredBikeList(PREDICATE_SHOW_ALL_BIKES);
        modelManager.updateFilteredLoanList(PREDICATE_SHOW_ALL_LOANS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLoanBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(loanBook, differentUserPrefs)));
    }
}

package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysPerson;

import org.junit.Test;

import guitests.guihandles.PersonCardHandle;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.PersonBuilder;

public class LoanCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Loan loanWithNoTags = new PersonBuilder().withTags(new String[0]).build();
        PersonCard personCard = new PersonCard(loanWithNoTags, 1);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, loanWithNoTags, 1);

        // with tags
        Loan loanWithTags = new PersonBuilder().build();
        personCard = new PersonCard(loanWithTags, 2);
        uiPartRule.setUiPart(personCard);
        assertCardDisplay(personCard, loanWithTags, 2);
    }

    @Test
    public void equals() {
        Loan loan = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(loan, 0);

        // same loan, same index -> returns true
        PersonCard copy = new PersonCard(loan, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different loan, same index -> returns false
        Loan differentLoan = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentLoan, 0)));

        // same loan, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(loan, 1)));
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedLoan} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PersonCard personCard, Loan expectedLoan, int expectedId) {
        guiRobot.pauseForHuman();

        PersonCardHandle personCardHandle = new PersonCardHandle(personCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", personCardHandle.getId());

        // verify loan details are displayed correctly
        assertCardDisplaysPerson(expectedLoan, personCardHandle);
    }
}

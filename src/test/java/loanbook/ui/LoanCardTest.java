package loanbook.ui;

import static loanbook.ui.testutil.GuiTestAssert.assertCardDisplaysLoan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.LoanCardHandle;
import loanbook.model.loan.Loan;
import loanbook.testutil.LoanBuilder;

public class LoanCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Loan loanWithNoTags = new LoanBuilder().withTags(new String[0]).build();
        LoanCard loanCard = new LoanCard(loanWithNoTags, 1);
        uiPartRule.setUiPart(loanCard);
        assertCardDisplay(loanCard, loanWithNoTags, 1);

        // with tags
        Loan loanWithTags = new LoanBuilder().build();
        loanCard = new LoanCard(loanWithTags, 2);
        uiPartRule.setUiPart(loanCard);
        assertCardDisplay(loanCard, loanWithTags, 2);
    }

    @Test
    public void equals() {
        Loan loan = new LoanBuilder().build();
        LoanCard loanCard = new LoanCard(loan, 0);

        // same loan, same index -> returns true
        LoanCard copy = new LoanCard(loan, 0);
        assertTrue(loanCard.equals(copy));

        // same object -> returns true
        assertTrue(loanCard.equals(loanCard));

        // null -> returns false
        assertFalse(loanCard == null);

        // different types -> returns false
        assertFalse(loanCard.equals(0));

        // different loan, same index -> returns false
        Loan differentLoan = new LoanBuilder().withName("differentName").build();
        assertFalse(loanCard.equals(new LoanCard(differentLoan, 0)));

        // same loan, different index -> returns false
        assertFalse(loanCard.equals(new LoanCard(loan, 1)));
    }

    /**
     * Asserts that {@code loanCard} displays the details of {@code expectedLoan} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(LoanCard loanCard, Loan expectedLoan, int expectedId) {
        guiRobot.pauseForHuman();

        LoanCardHandle loanCardHandle = new LoanCardHandle(loanCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId), loanCardHandle.getId());

        // verify loan details are displayed correctly
        assertCardDisplaysLoan(expectedLoan, loanCardHandle);
    }
}

package seedu.address.ui.testutil;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.LoanCardHandle;
import guitests.guihandles.LoanListPanelHandle;
import guitests.guihandles.ResultDisplayHandle;
import seedu.address.model.loan.Loan;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(LoanCardHandle expectedCard, LoanCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getNric(), actualCard.getNric());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLoan}.
     */
    public static void assertCardDisplaysLoan(Loan expectedLoan, LoanCardHandle actualCard) {
        assertEquals(expectedLoan.getLoanerName().value, actualCard.getName());
        assertEquals(expectedLoan.getLoanerNric().value, actualCard.getNric());
        assertEquals(expectedLoan.getLoanerPhone().value, actualCard.getPhone());
        assertEquals(expectedLoan.getLoanerEmail().value, actualCard.getEmail());
        assertEquals(expectedLoan.getAddress().value, actualCard.getAddress());
        assertEquals(expectedLoan.getTags().stream().map(tag -> tag.value).collect(Collectors.toList()),
                actualCard.getTags());
    }

    /**
     * Asserts that the list in {@code loanListPanelHandle} displays the details of {@code loans} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LoanListPanelHandle loanListPanelHandle, Loan... loans) {
        for (int i = 0; i < loans.length; i++) {
            loanListPanelHandle.navigateToCard(i);
            assertCardDisplaysLoan(loans[i], loanListPanelHandle.getLoanCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code loanListPanelHandle} displays the details of {@code loans} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LoanListPanelHandle loanListPanelHandle, List<Loan> loans) {
        assertListMatching(loanListPanelHandle, loans.toArray(new Loan[0]));
    }

    /**
     * Asserts the size of the list in {@code loanListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(LoanListPanelHandle loanListPanelHandle, int size) {
        int numberOfLoans = loanListPanelHandle.getListSize();
        assertEquals(size, numberOfLoans);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}

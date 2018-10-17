package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class LoanIdTest {

    private static final int EXPECTED_MAXIMUM_LOANID = 999999999;

    @Test
    public void isValidLoanIdTest() {
        assertTrue(LoanId.isValidLoanId("0")); // Zero
        assertTrue(LoanId.isValidLoanId("7")); // Minimum number of digits
        assertTrue(LoanId.isValidLoanId("1745")); // Standard number
        assertTrue(LoanId.isValidLoanId("0002103")); // Leading zeroes are okay as long as not more than 9 digits
        assertTrue(LoanId.isValidLoanId("123456789")); // Maximum number of digits
        assertTrue(LoanId.isValidLoanId("999999999")); // Largest possible ID

        assertFalse(LoanId.isValidLoanId("")); // Empty string
        assertFalse(LoanId.isValidLoanId("    ")); // All spaces
        assertFalse(LoanId.isValidLoanId("-50")); // Negative number
        assertFalse(LoanId.isValidLoanId("842 747")); // Space in the ID
        assertFalse(LoanId.isValidLoanId("failure")); // Non-numeric characters
        assertFalse(LoanId.isValidLoanId("1234567890")); // More than 9 digits
        assertFalse(LoanId.isValidLoanId("0000000000")); // 10 digits, even though the number does not exceed 10^10.
    }

    @Test
    public void constructorTest() {
        LoanId id1 = new LoanId("245"); // Standard ID
        LoanId id2 = new LoanId("0"); // Zero
        LoanId id3 = new LoanId("000000000"); // Zero with leading zeroes
        LoanId id4 = new LoanId("0008472"); // Leading zeroes
        LoanId id5 = new LoanId("999999999"); // Maximum value

        assertEquals((int) id1.value, 245);
        assertEquals((int) id2.value, 0);
        assertEquals((int) id3.value, 0);
        assertEquals((int) id4.value, 8472);
        assertEquals((int) id5.value, 999999999);
    }

    @Test
    public void equalityTest() {
        LoanId id1 = new LoanId("8472"); // Standard ID
        LoanId id2 = new LoanId("8472"); // Same value as id1
        LoanId id3 = new LoanId("924"); // Different value from id1
        LoanId id4 = new LoanId("000924"); // Same value as id3, but with leading zeroes

        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id2, id3);
        assertEquals(id3, id4);
    }

    @Test
    public void invalidLoanIdConstructionException() {
        // Trying to construct a new Loan ID with an invalid string should throw an IllegalArgumentException.
        Assert.assertThrows(IllegalArgumentException.class, () -> new LoanId("badId"));
    }

    @Test
    public void maximumLoanIdTest() {
        LoanId maxLoanId = new LoanId(Integer.toString(EXPECTED_MAXIMUM_LOANID)); // The expected maximum
        LoanId normalLoanId = new LoanId("4858"); // A standard Loan ID

        assertTrue(maxLoanId.isMaximumId());
        assertFalse(normalLoanId.isMaximumId());
    }
}

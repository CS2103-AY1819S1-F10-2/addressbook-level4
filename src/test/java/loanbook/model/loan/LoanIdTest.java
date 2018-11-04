package loanbook.model.loan;

import static loanbook.testutil.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoanIdTest {

    private static final int EXPECTED_MAXIMUM_LOAN_ID = 999999999;

    @Test
    public void isValidLoanIdStringTest() {
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
    public void isValidLoanIdIntegerTest() {
        assertTrue(LoanId.isValidLoanId(0)); // Zero
        assertTrue(LoanId.isValidLoanId(8245)); // Standard number
        assertTrue(LoanId.isValidLoanId(123456789)); // Maximum number of digits
        assertTrue(LoanId.isValidLoanId(999999999)); // Largest possible ID

        assertFalse(LoanId.isValidLoanId(-1)); // Negative number
        assertFalse(LoanId.isValidLoanId(-1234567890)); // Extremely negative number
        assertFalse(LoanId.isValidLoanId(1234567890)); // More than 9 digits
    }

    @Test
    public void constructorTest() {
        LoanId id1 = new LoanId("245"); // Standard ID
        LoanId id2 = new LoanId("0"); // Zero
        LoanId id3 = new LoanId("000000000"); // Zero with leading zeroes
        LoanId id4 = new LoanId("0008472"); // Leading zeroes
        LoanId id5 = new LoanId("999999999"); // Maximum value

        assertEquals(245, (int) id1.value);
        assertEquals(0, (int) id2.value);
        assertEquals(0, (int) id3.value);
        assertEquals(8472, (int) id4.value);
        assertEquals(999999999, (int) id5.value);
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
        assertNotEquals(id1, "NotEvenALoanID");
    }

    @Test
    public void invalidLoanIdConstructionException() {
        // Trying to construct a new Loan ID with an invalid string should throw an IllegalArgumentException.
        assertThrows(IllegalArgumentException.class, () -> new LoanId("badId"));
    }

    @Test
    public void maximumLoanIdTest() {
        LoanId maxLoanIdFromString = new LoanId(Integer.toString(EXPECTED_MAXIMUM_LOAN_ID)); // The expected maximum
        LoanId maxLoanIdFromInt = LoanId.fromInt(EXPECTED_MAXIMUM_LOAN_ID);
        LoanId normalLoanId = new LoanId("4858"); // A standard Loan ID

        assertTrue(maxLoanIdFromString.isMaximumId());
        assertTrue(maxLoanIdFromInt.isMaximumId());
        assertFalse(normalLoanId.isMaximumId());
    }

    @Test
    public void integerConstructorTest() {
        LoanId loanId1Int = LoanId.fromInt(404); // Standard Loan ID constructed from int
        LoanId loanId1String = new LoanId("404"); // Same ID constructed from String

        LoanId loanId2Int = LoanId.fromInt(287472); // Standard Loan ID constructed from int
        LoanId loanId2String = new LoanId("287472"); // Same ID constructed from String

        assertEquals(404, (int) loanId1Int.value);
        assertEquals(loanId1Int, loanId1String);

        assertEquals(287472, (int) loanId2Int.value);
        assertEquals(loanId2Int, loanId2String);
    }

    @Test
    public void invalidIntegerConstructorTest() {
        assertThrows(IllegalArgumentException.class, () -> LoanId.fromInt(-1)); // Negative number
        assertThrows(IllegalArgumentException.class, () -> LoanId.fromInt(1234567890)); // More than 9 digits.
    }
}

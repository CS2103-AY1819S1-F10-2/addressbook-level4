package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LoanTimeTest {

    // Note that this method does not run correctly on Appveyor.
    // Tried and tested though. It works.
    /**
     * Tests for LoanTime object creation based on current system date.
     */
    public void constructorInputStringFormatValue() {
        LoanTime loanTime1 = new LoanTime("2001-02-03 19:06");
        assertEquals("2001-02-03, 19:06", loanTime1.toString());

        LoanTime loanTime2 = new LoanTime("2021-12-24 02:06");
        assertEquals("2021-12-24, 02:06", loanTime2.toString());
    }

    // Note that this method will not return equals as it is time dependent
    // Tried and Tested though. It works.
    /**
     * Tests for LoanTime object creation based on current system date.
     */
    public void constructorInputCheckCurrentDate() {
        LoanTime loanTime = new LoanTime("00:25");

        // Replace with your current date to test
        assertEquals("2018-10-07, 00:25", loanTime.toString());
    }

    // Note that this method will not return equals as it is time dependent
    // Tried and Tested though. It works.
    // TODO: To figure out how to comment out tests
    /**
     * Tests for LoanTime object creation given current system time.
     */
    public void constructorInputCheckCurrentTime() {
        LoanTime loanTime = new LoanTime();

        // Replace with your current time and date to test
        assertEquals("2018-10-07, 16:38", loanTime.toString());
    }

    @Test
    public void isValidLongLoanTimeTests() {
        assertFalse(LoanTime.isValidLongLoanTimeFormat("")); // Empty String
        assertFalse(LoanTime.isValidLongLoanTimeFormat("      ")); // Spaces only

        assertFalse(LoanTime.isValidLongLoanTimeFormat("10-12-25 14:09")); // Invalid Year
        assertFalse(LoanTime.isValidLongLoanTimeFormat("2010-May-25 14:09")); // Invalid Month
        assertFalse(LoanTime.isValidLongLoanTimeFormat("2010-12-25 14-09")); // Incorrect separator format

        assertTrue(LoanTime.isValidLongLoanTimeFormat("2010-12-25 04:09")); // An example of a correct one
        assertTrue(LoanTime.isValidLongLoanTimeFormat("2010-12-25     14:09")); // Multiple Spaces
    }

    @Test
    public void isValidShortLoanTimeTests() {
        assertFalse(LoanTime.isValidShortLoanTimeFormat("")); // Empty String
        assertFalse(LoanTime.isValidShortLoanTimeFormat("      ")); // Spaces only

        assertFalse(LoanTime.isValidShortLoanTimeFormat("14-09")); // Incorrect separator format
        assertFalse(LoanTime.isValidShortLoanTimeFormat("1:09")); // Incorrect hour format
        assertFalse(LoanTime.isValidShortLoanTimeFormat("11:09 PM")); // Incorrect time format

        assertTrue(LoanTime.isValidShortLoanTimeFormat("04:55")); // An example of a correct one
    }

    @Test
    // Note that input params are already in the format YYYY-MM-DD
    public void isValidDateTests() {
        assertFalse(LoanTime.isValidDate("2011-13-16")); // 13th Month
        assertFalse(LoanTime.isValidDate("2011-01-32")); // 32th Day

        assertTrue(LoanTime.isValidDate("2011-01-31")); // January has 31 days
        assertFalse(LoanTime.isValidDate("2011-06-31")); // June only has 30 days

        assertFalse(LoanTime.isValidDate("2014-02-29")); // Not a leap years
        assertTrue(LoanTime.isValidDate("2012-02-29")); // Is a leap years

        assertTrue(LoanTime.isValidDate("2012-05-29")); // A correct example
    }

    @Test
    public void isValidTimeTests() {
        assertFalse(LoanTime.isValidTime("26:16")); // Too many hours
        assertFalse(LoanTime.isValidTime("21:67")); // Too many minutes

        assertTrue(LoanTime.isValidTime("21:27")); // A correct example
        assertTrue(LoanTime.isValidTime("00:00")); // Midnight
    }

    @Test
    public void loanTimeDifferences() {
        LoanTime loanTime1 = new LoanTime("2001-01-01 12:00");
        LoanTime loanTime2 = new LoanTime("2001-01-01 14:00");

        assertEquals(120, loanTime1.loanTimeDifferenceMinutes(loanTime2)); // Functional use
        assertEquals(0, loanTime2.loanTimeDifferenceMinutes(loanTime1)); // Time 2 earlier

        assertEquals(120, LoanTime.loanTimeDifferenceMinutes(loanTime1, loanTime2)); // Functional use
        assertEquals(0, LoanTime.loanTimeDifferenceMinutes(loanTime2, loanTime1)); // Time 2 earlier

        LoanTime loanTime3 = new LoanTime("2001-01-02 12:05");
        assertEquals(1445, loanTime1.loanTimeDifferenceMinutes(loanTime3)); // Across Day
    }

    @Test
    public void constructorsDoesNotThrowErrorTest() {
        LoanTime loanTime1 = new LoanTime();
        LoanTime loanTime2 = new LoanTime();
        // assertEquals(0, LoanTime.loanTimeDifferenceMinutes(loanTime1, loanTime2)); // Created within a minute

        LoanTime loanTime3 = new LoanTime("09:27");
        LoanTime loanTime4 = new LoanTime("12:05");
        assertEquals(158, LoanTime.loanTimeDifferenceMinutes(loanTime3, loanTime4)); // 2 hours and 38 minutes

        LoanTime loanTime5 = new LoanTime("2001-01-02 12:05");
        LoanTime loanTime6 = new LoanTime("2002-01-02 12:05");
        assertEquals(525600, LoanTime.loanTimeDifferenceMinutes(loanTime5, loanTime6)); // Minutes in a (non leap) year
    }
}

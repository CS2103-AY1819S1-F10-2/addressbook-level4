package seedu.address.model.loan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class LoanTimeTest {

    private static final DateTimeFormatter EXPECTED_DATE_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static final DateTimeFormatter EXPECTED_DATETIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

    /**
     * Tests for LoanTime object creation based on current system date.
     */
    @Test
    public void constructorInputStringFormatValue() {
        LoanTime loanTime1 = new LoanTime("2001-02-03 19:06");
        assertEquals("2001-02-03 19:06", loanTime1.toString());

        LoanTime loanTime2 = new LoanTime("2021-12-24 02:06");
        assertEquals("2021-12-24 02:06", loanTime2.toString());

        LoanTime loanTime3 = new LoanTime("2103-01-01 21:03");
        assertEquals("2103-01-01 21:03", loanTime3.toString());
    }

    /**
     * Tests for LoanTime object creation based on current system date.
     */
    @Test
    public void constructorInputCheckCurrentDate() {
        // The LoanTime constructor that only takes in the Time (and not the Date)
        // will use the current system's date. To test it, we'll manually get the
        // current date and test it accordingly.
        //
        // However, it's possible (though very unlikely) that the date will change
        // between the next two statements. Therefore, we'll also have the current
        // date after the LoanTime construction. The constructed LoanTime must be
        // equal to one of them.
        String currentDateBeforeLoanTimeCreation = LocalDate.now().format(EXPECTED_DATE_FORMAT);
        LoanTime loanTime1 = new LoanTime("00:25");
        LoanTime loanTime2 = new LoanTime("21:03");
        String currentDateAfterLoanTimeCreation = LocalDate.now().format(EXPECTED_DATE_FORMAT);

        String loanTime1ToString = loanTime1.toString();
        String loanTime2ToString = loanTime2.toString();

        assertTrue(loanTime1ToString.equals(currentDateBeforeLoanTimeCreation + " 00:25")
                || loanTime1ToString.equals(currentDateAfterLoanTimeCreation + " 00:25"));

        assertTrue(loanTime2ToString.equals(currentDateBeforeLoanTimeCreation + " 21:03")
                || loanTime2ToString.equals(currentDateAfterLoanTimeCreation + " 21:03"));
    }

    /**
     * Tests for LoanTime object creation given current system time.
     */
    @Test
    public void constructorInputCheckCurrentTime() {
        LocalDateTime currentDateTimeBeforeLoanTimeCreation = LocalDateTime.now().withSecond(0).withNano(0);
        LoanTime loanTime = new LoanTime();
        LocalDateTime currentDateTimeAfterLoanTimeCreation = LocalDateTime.now().withSecond(0).withNano(0);

        LocalDateTime loanTimeAsLocalDateTime = LocalDateTime.parse(loanTime.toString(), EXPECTED_DATETIME_FORMAT);

        // The expected results are that "dateTimeBeforeCreation <= loanTime <= dateTimeAfterCreation".
        // Since the LocalDateTime class does not have a "isBeforeOrEquals" method, we will use the inverse,
        // which is !isAfter.
        assertFalse(currentDateTimeBeforeLoanTimeCreation.isAfter(loanTimeAsLocalDateTime));
        assertFalse(currentDateTimeAfterLoanTimeCreation.isBefore(loanTimeAsLocalDateTime));
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
    public void isValidLoanTimeTests() {
        assertFalse(LoanTime.isValidLoanTime("")); // Empty string
        assertFalse(LoanTime.isValidLoanTime("      ")); // Spaces only

        assertFalse(LoanTime.isValidLoanTime("100-12-25 14:09")); // Invalid Year
        assertFalse(LoanTime.isValidLoanTime("2010-Jan-01 14:09")); // Invalid Month
        assertFalse(LoanTime.isValidLoanTime("2010-12-25 14.09")); // Incorrect separator format

        assertFalse(LoanTime.isValidLoanTime("14.09")); // Incorrect separator format
        assertFalse(LoanTime.isValidLoanTime("1:09")); // Incorrect hour format
        assertFalse(LoanTime.isValidLoanTime("11:09 PM")); // Incorrect time format
        assertFalse(LoanTime.isValidLoanTime("2103")); // No separator

        // Examples of valid short and long formatted strings.
        assertTrue(LoanTime.isValidLoanTime("04:55")); // Short format
        assertTrue(LoanTime.isValidLoanTime("2010-12-25 04:09")); // Long format
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
    public void loanEqualityTest() {
        LoanTime loanTime1 = new LoanTime("2103-01-01 12:00");
        LoanTime loanTime2 = new LoanTime("2103-01-01 12:00");
        LoanTime loanTime3 = new LoanTime("2103-01-02 12:00");
        LoanTime loanTime4 = new LoanTime("2103-01-01 12:30");

        assertEquals(loanTime1, loanTime2);
        assertNotEquals(loanTime1, loanTime3); // Different date
        assertNotEquals(loanTime1, loanTime4); // Different time
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

    @Test
    public void toStringIsValidInputTest() {
        String loanTimeString1 = "2103-01-03 21:03";
        LoanTime loanTime1 = new LoanTime(loanTimeString1);
        String loanTime1ToString = loanTime1.toString();

        // The output of toString should match the initial input string.
        assertEquals(loanTimeString1, loanTime1ToString);

        // If used to create another LoanTime object, it should be equivalent.
        LoanTime loanTime2 = new LoanTime(loanTime1ToString);
        assertEquals(loanTime1, loanTime2);

        // Test the same thing with a LoanTime created using only a time string.
        LoanTime loanTime3 = new LoanTime("20:00");
        LoanTime loanTime4 = new LoanTime(loanTime3.toString());

        assertEquals(loanTime3, loanTime4);
    }
}

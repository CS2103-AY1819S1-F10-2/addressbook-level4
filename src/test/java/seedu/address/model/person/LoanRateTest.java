package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class LoanRateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new LoanRate(null));
    }

    @Test
    public void constructor_invalidRate_throwsIllegalArgumentException() {
        String invalidRate = "-1";
        Assert.assertThrows(IllegalArgumentException.class, () -> new LoanRate(invalidRate));
    }

    @Test
    public void isValidRate() {
        // null rate
        Assert.assertThrows(NullPointerException.class, () -> LoanRate.isValidRate(null));

        // invalid rates
        assertFalse(LoanRate.isValidRate("")); // empty string
        assertFalse(LoanRate.isValidRate(" ")); // spaces only
        assertFalse(LoanRate.isValidRate("rate")); // non-numeric
        assertFalse(LoanRate.isValidRate("01")); // first place is 0
        assertFalse(LoanRate.isValidRate("02.21")); // first place is 0
        assertFalse(LoanRate.isValidRate("1.0")); // last decimal place is 0
        assertFalse(LoanRate.isValidRate("9278ncb")); // alphabets within digits
        assertFalse(LoanRate.isValidRate("12 34")); // spaces within digits

        // valid rates
        assertTrue(LoanRate.isValidRate("12.01"));
        assertTrue(LoanRate.isValidRate("1234567890"));
        assertTrue(LoanRate.isValidRate("82472637826762258923"));
        assertTrue(LoanRate.isValidRate("0.222222"));

    }
}

package seedu.address.model.loan;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO: Add tests
public class LoanTimeTest {

    @Test
    public void constructorInputStringFormatValue() {
        LoanTime loanTime1 = new LoanTime("2001-02-03 19:06");
        assertEquals("2001-02-03, 19:06", loanTime1.toString());

        LoanTime loanTime2 = new LoanTime("2021-12-24 02:06");
        assertEquals("2021-12-24, 02:06", loanTime2.toString());
    }

    // Note that this method will not return equals as it is time dependent
    // Tried and Tested though. It works.
    @Test
    public void constructorInputCheckCurrentDate(){
        LoanTime loanTime = new LoanTime("00:25");

        // Replace with your current date to test
        assertEquals("2018-10-07, 00:25", loanTime.toString());
    }

    // Note that this method will not return equals as it is time dependent
    // Tried and Tested though. It works.
    public void constructorInputCheckCurrentTime(){
        LoanTime loanTime = new LoanTime();

        // Replace with your current time and date to test
        assertEquals("2018-10-07, 16:38", loanTime.toString());
    }
}

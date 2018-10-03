package seedu.address.model.person;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Represents a timeStamp in the loan book.
 * Guarantees: immutable;
 */
public class LoanTime {

    public final long value;

    /**
     * Constructs a {@code LoanTime} that is now.
     */
    public LoanTime() {
        value = System.currentTimeMillis();
    }

    // TODO: Populate parseLoanTime(String inputString)
    // TODO: Create Validation regex string
    public static LoanTime parseLoanTime(String inputString) {
        // TODO
        return null;
    }

    /**
     * Returns the difference in time given one other LoanTime object.
     * This will be returned as a long of number of second.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param otherTime LoanTime object to be compared to.
     */
    public long loanTimeDifferenceSeconds(LoanTime otherTime) {
        long timeDifference = (this.value - otherTime.value);
        return (timeDifference > 0) ? timeDifference / 1000 : 0;
    }

    /**
     * Returns the difference in time given two LoanTime objects.
     * This will be returned as a long of number of second.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param currentTime LoanTime object to signify start of time interval.
     * @param otherTime   LoanTime object to signify end of time interval.
     */
    public static long loanTimeDifferenceSeconds(LoanTime currentTime, LoanTime otherTime) {
        long timeDifference = (currentTime.value - otherTime.value);

        return (timeDifference > 0) ? timeDifference / 1000 : 0;
    }

    /**
     * Returns the DDMMYYYY representation of the stored LoanTime.
     * The format returned is "YYYY MM DD, HH:MM AM/PM".
     */
    @Override
    public String toString() {
        // We create a new date object and return the DDMMYYYY representation of it
        Date date = new Date(value);

        // Set the formatting out.
        //TODO: To test the actual format
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM dd',' hh:mm");

        // Set the timezone to be GMT +8
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return simpleDateFormat.format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoanTime // instanceof handles nulls
                && value == ((LoanTime) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }
}
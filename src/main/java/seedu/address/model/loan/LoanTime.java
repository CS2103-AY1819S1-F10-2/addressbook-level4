package seedu.address.model.loan;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Represents a timeStamp in the loan book.
 * Guarantees: immutable;
 */
public class LoanTime {

    public static final String MESSAGE_LOANTIME_CONSTRAINTS =
            "LoanTime specified has to be either in the format 'YYYY-MM-DD HH:mm' or 'HH:mm'.";

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date specified has to be valid, in the format 'YYYY-MM-DD'.";

    public static final String MESSAGE_TIME_CONSTRAINTS =
            "Date specified has to be valid, in the format 'HH:mm'. HH should be in 24 hour format";

    /*
     * 2 versions of the regex:
     * A long version where a date is specified
     * Format: YYYY-MM-DD HH:mm
     *
     * A short version where only the time is specified
     * Format HH:MM
     */
    public static final String LONG_LOANTIME_VALIDATION_REGEX = "^\\d{4}-\\d{2}-\\d{2} +\\d{2}:\\d{2}";
    public static final String SHORT_LOANTIME_VALIDATION_REGEX = "^\\d{2}:\\d{2}";

    public final Instant value;

    /**
     * Constructs a {@code LoanTime} with value set at current time.
     */
    public LoanTime() {
        value = Instant.now();
    }

    /**
     * Constructs an {@code LoanTime}.
     *
     * @param loanTime A valid loanTime.
     */
    public LoanTime(String loanTime) {
        requireNonNull(loanTime);
        checkArgument(isValidLongLoanTimeFormat(loanTime) || isValidShortLoanTimeFormat(loanTime),
                MESSAGE_LOANTIME_CONSTRAINTS);

        // Create the Strings to pass into parse method
        StringBuilder stringBuilder = new StringBuilder();

        // Check the date
        if (isValidLongLoanTimeFormat(loanTime)) {
            String[] dateData = loanTime.split(" ");
            checkArgument(isValidDate(dateData[0]), MESSAGE_DATE_CONSTRAINTS);
            stringBuilder.append(dateData[0]);
            loanTime = dateData[1];
        } else {
            DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
            Date date = new Date();
            stringBuilder.append(dateFormat.format(date));
        }

        stringBuilder.append("T");

        // Check and append the time
        checkArgument(isValidTime(loanTime), MESSAGE_TIME_CONSTRAINTS);
        stringBuilder.append(loanTime);

        // Append the "Z" and other padding.
        stringBuilder.append(":00.00Z");

        // Find a way to parse the input date correctly...
        // I hardcoded in the -8 Hours GMT into here.
        value = Instant.parse(stringBuilder.toString()).minusSeconds(8 * 60 * 60);
    }

    /**
     * Returns if a given string is a valid long LoanTime.
     */
    public static boolean isValidLongLoanTimeFormat(String test) {
        return test.matches(LONG_LOANTIME_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a valid short LoanTime.
     */
    public static boolean isValidShortLoanTimeFormat(String test) {
        return test.matches(SHORT_LOANTIME_VALIDATION_REGEX);
    }

    /**
     * Returns if a given string is a valid Date.
     * If there is a better way of doing this without relying on thrown exceptions
     */
    public static boolean isValidDate(String test) {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        sdf.setLenient(false);

        try {
            sdf.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns if a given string is a valid Time.
     */
    public static boolean isValidTime(String test) {
        String[] timeData = test.split(":");
        if (timeData.length <= 1) {
            return false;
        }
        return (Integer.parseInt(timeData[0]) < 24) && (Integer.parseInt(timeData[1]) < 60);
    }

    /**
     * Returns the difference in time given one other LoanTime object.
     * This will be returned as a long of number of second.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param otherTime LoanTime object to be compared to.
     */
    public long loanTimeDifferenceMinutes(LoanTime otherTime) {
        long timeDifference = (otherTime.value.toEpochMilli() - this.value.toEpochMilli());
        return (timeDifference >= 0) ? timeDifference / 60000 : 0;
    }

    /**
     * Returns the difference in time given two LoanTime objects.
     * This will be returned as a long of number of second.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param currentTime LoanTime object to signify start of time interval.
     * @param otherTime   LoanTime object to signify end of time interval.
     */
    public static long loanTimeDifferenceMinutes(LoanTime currentTime, LoanTime otherTime) {
        long timeDifference = (otherTime.value.toEpochMilli() - currentTime.value.toEpochMilli());
        return (timeDifference >= 0) ? timeDifference / 60000 : 0;
    }

    /**
     * Returns the DDMMYYYY representation of the stored LoanTime.
     * The format returned is "YYYY-MM-DD, HH:MM".
     */
    @Override
    public String toString() {
        // We create a new Date object and return the DDMMYYYY representation of it
        Date date = new Date(value.getEpochSecond() * 1000);

        // Set the formatting out.
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd',' HH:mm");

        return simpleDateFormat.format(date);
    }
}

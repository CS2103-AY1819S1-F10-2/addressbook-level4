package seedu.address.model.loan;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a timeStamp in the loan book.
 * Guarantees: immutable;
 */
public class LoanTime extends DataField<Instant> {

    public static final String MESSAGE_LOANTIME_CONSTRAINTS =
            "LoanTime specified has to be either in the format 'YYYY-MM-DD HH:mm' or 'HH:mm'. "
                    + "HH should be in 24 hour format";

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

    // Default patterns for Date and Time
    private static final String DEFAULT_DATE_PATTERN = "uuuu-MM-dd";
    private static final String DEFAULT_TIME_PATTERN = "HH:mm";

    // Formatters for Date and Time
    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter
            .ofPattern(DEFAULT_DATE_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter
            .ofPattern(DEFAULT_TIME_PATTERN)
            .withResolverStyle(ResolverStyle.STRICT);

    // Default pattern for DateTime
    private static final String DEFAULT_DATETIME_PATTERN = "uuuu-MM-dd HH:mm";

    /**
     * Constructs a {@code LoanTime} with value set at current time.
     */
    public LoanTime() {
        super(MESSAGE_LOANTIME_CONSTRAINTS, emptyString -> true, emptyString -> Instant.now(), "");
    }

    /**
     * Constructs an {@code LoanTime} by parsing the specified string.
     *
     * @param loanTime A string to be parsed into a LoanTime
     */
    public LoanTime(String loanTime) {
        super(MESSAGE_LOANTIME_CONSTRAINTS, LoanTime::isValidLoanTime, LoanTime::parseInstant, loanTime);
    }

    /**
     * Checks if a given string can be parsed into a valid LoanTime instance.
     *
     * @param objString the string to test
     * @return true if the string can be parsed into a valid LoanTime instance
     */
    public static boolean isValidLoanTime(String objString) {
        // First, check if the format is correct.
        if (!isValidLongLoanTimeFormat(objString) && !isValidShortLoanTimeFormat(objString)) {
            return false;
        }

        // Next, try and check for each case.
        if (isValidLongLoanTimeFormat(objString)) {
            // The string contains the Date and Time.
            String[] dateData = objString.split(" ");

            assert dateData.length == 2;

            String loanDateString = dateData[0];
            String loanTimeString = dateData[1];

            // Check if the Date and Time are valid.
            return isValidDate(loanDateString) && isValidTime(loanTimeString);
        } else {
            // The string only contains the Time. Check if the Time is valid.
            return isValidTime(objString);
        }
    }

    /**
     * Checks if a given string is a valid long LoanTime.
     *
     * @param test The string to test
     * @return true if the string is a valid long LoanTime
     */
    public static boolean isValidLongLoanTimeFormat(String test) {
        return test.matches(LONG_LOANTIME_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid short LoanTime.
     *
     * @param test The string to test
     * @return true if the string is a valid short LoanTime
     */
    public static boolean isValidShortLoanTimeFormat(String test) {
        return test.matches(SHORT_LOANTIME_VALIDATION_REGEX);
    }

    /**
     * Checks if a given string is a valid Date.
     *
     * @param test The string to test
     * @return true if the string is a valid Date
     */
    public static boolean isValidDate(String test) {
        try {
            DEFAULT_DATE_FORMATTER.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }

        return true;
    }

    /**
     * Checks if a given string is a valid Time.
     *
     * @param test The string to test
     * @return true if the string is a valid Time
     */
    public static boolean isValidTime(String test) {
        String[] timeData = test.split(":");
        assert timeData.length == 2;

        return (Integer.parseInt(timeData[0]) < 24) && (Integer.parseInt(timeData[1]) < 60);
    }

    /**
     * Returns the difference in time given one other LoanTime object.
     * This will be returned as a long of number of minutes.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param otherTime LoanTime object to be compared to.
     */
    public long loanTimeDifferenceMinutes(LoanTime otherTime) {
        return loanTimeDifferenceMinutes(this, otherTime);
    }

    /**
     * Returns the difference in time given two LoanTime objects.
     * This will be returned as a long of number of minutes.
     * Function returns 0 if the specified other LoanTime is before the current LoanTime.
     *
     * @param currentTime LoanTime object to signify start of time interval.
     * @param otherTime   LoanTime object to signify end of time interval.
     */
    public static long loanTimeDifferenceMinutes(LoanTime currentTime, LoanTime otherTime) {
        long timeDifference = Duration.between(currentTime.value, otherTime.value).toMinutes();
        return (timeDifference >= 0) ? timeDifference : 0;
    }

    /**
     * Parses the specified string into an {@link Instant}. This method will use the current System
     * time zone during the parsing process.
     *
     * @param objString The string to parse
     * @return The parsed {@link Instant}
     */
    private static Instant parseInstant(String objString) {
        // If the string is not valid, throw an exception.
        if (!isValidLoanTime(objString)) {
            throw new IllegalArgumentException(MESSAGE_LOANTIME_CONSTRAINTS);
        }

        // Prepare the Date and Time fields.
        LocalDate loanLocalDate;
        LocalTime loanLocalTime;

        // Consider both cases.
        if (isValidLongLoanTimeFormat(objString)) {
            // Split by spaces to separate the Date and Time strings
            String[] dateData = objString.split(" ");

            assert dateData.length == 2;

            // Obtain the Date and Time strings
            String loanDateString = dateData[0];
            String loanTimeString = dateData[1];

            // Parse the Date and Time strings.
            loanLocalDate = LocalDate.parse(loanDateString, DEFAULT_DATE_FORMATTER);
            loanLocalTime = LocalTime.parse(loanTimeString, DEFAULT_TIME_FORMATTER);
        } else {
            // If the string does not match the LongLoanTimeFormat,
            // then it should match the ShortLoanTimeFormat.
            assert isValidShortLoanTimeFormat(objString);

            // The date used shall be today's date.
            loanLocalDate = LocalDate.now();
            // Parse the Time string.
            loanLocalTime = LocalTime.parse(objString, DEFAULT_TIME_FORMATTER);
        }

        // Set the Time Zone as the System default time zone.
        ZonedDateTime loanZonedDateTime = ZonedDateTime.of(loanLocalDate, loanLocalTime, ZoneId.systemDefault());

        // Convert the ZonedDateTime into an Instant.
        return Instant.from(loanZonedDateTime);
    }

    /**
     * Returns the YYYY-MM-DD, HH:MM representation of the stored LoanTime.
     * The format returned is "YYYY-MM-DD HH:MM".
     */
    @Override
    public String toString() {
        // Create a DateTimeFormatter to format the Instant.
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter
                .ofPattern(DEFAULT_DATETIME_PATTERN) // Set the pattern to be the default pattern.
                .withZone(ZoneId.systemDefault()); // Set the time zone as the current time zone

        return dateTimeFormatter.format(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LoanTime)) {
            return false;
        }

        return this.value.getEpochSecond() == ((LoanTime) other).value.getEpochSecond();

    }
}

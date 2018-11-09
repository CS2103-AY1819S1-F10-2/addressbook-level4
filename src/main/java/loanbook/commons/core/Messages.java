package loanbook.commons.core;

import loanbook.logic.commands.HelpCommand;

/**
 * Container for user visible messages.
 */
public class Messages {

    /* General */
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command."
            + " Type in the `" + HelpCommand.COMMAND_WORD + "` command (or press F1)"
            + " to view a list of available commands.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!"
            + "\nPlease check that all required parameter prefixes are present and correctly typed in."
            + "\n%1$s";

    /* Lists */
    public static final String MESSAGE_INVALID_LOAN_DISPLAYED_INDEX = "The loan index provided is invalid."
            + "Please ensure that the index is within the bounds of the list.";
    public static final String MESSAGE_LOANS_LISTED_OVERVIEW = "%1$d loans listed!";

    /* Bikes */
    public static final String MESSAGE_DUPLICATE_BIKE = "A bike with the same name already exists in the loan book.";
    public static final String MESSAGE_BIKE_NOT_FOUND = "No bike with that name exists within the loan book.";

    /* Editing */
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    /* Password */
    public static final String MESSAGE_INVALID_PASSWORD = "The password provided is incorrect!";
    public static final String MESSAGE_INVALID_OLD_PASS = "The old password entered is incorrect!";
    public static final String MESSAGE_SAME_AS_CURRENT_PASSWORD = "The password provided is same as before!";

    /* Date */
    public static final String MESSAGE_INVALID_DATE_FORMAT =
            "Dates provided are either an invalid date or they are in an invalid format.\n"
            + "Please use the format YYYY-MM-DD.";
    public static final String MESSAGE_INVALID_DATE_RANGE =
            "Dates provided are not in a valid range. The end date cannot be before the start date.";

    /* Email connection */
    public static final String MESSAGE_AUTHEN_FAILURE = "Your password might be wrong "
            + "or you did not enable \"less secure app\" in your google account setting before you use remind command."
            + " Please refer to the User Guide by pressing F1!";
    public static final String MESSAGE_NO_NETWORK_CONNECTION = "No connection to the network! "
            + "Please make sure you have network connected!";
    public static final String MESSAGE_BAD_RUNTIME = "You execute your code in a Java runtime"
            + " that does not support UTF-8!";

    /* Loan verification for email */
    public static final String MESSAGE_INVALID_INFO = "There is no Loan with this ID in the LoanBook!";
    public static final String MESSAGE_LOAN_IS_DONE = "There is no need to send a reminder, because the loan is %s";

    /* Email verification */
    public static final String MESSAGE_WRONG_OLDEMAIL = "The old email address is wrong!";
    public static final String MESSAGE_DUPLICATE_FAILURE = "The old email and the new email cannot be the same!";
    public static final String MESSAGE_INVALID_EMAIL = "Your new email address is invalid! It must be valid gmail!";
}

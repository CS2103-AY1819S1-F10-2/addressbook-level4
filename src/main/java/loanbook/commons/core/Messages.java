package loanbook.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_LOAN_DISPLAYED_INDEX = "The loan index provided is invalid";
    public static final String MESSAGE_LOANS_LISTED_OVERVIEW = "%1$d loans listed!";
    public static final String MESSAGE_INVALID_PASSWORD = "The password provided is incorrect!";
    public static final String MESSAGE_INVALID_OLD_PASS = "The old password entered is incorrect!";
    public static final String MESSAGE_SAME_AS_CURRENT_PASSWORD = "The password provided is same as before!";
    public static final String MESSAGE_AUTHEN_FAILURE = "Your password might be wrong "
            + "or you did not enable \"less secure app\" in your google account setting before you use remind command."
            + " Please refer to the User Guide by pressing F1!";
    public static final String MESSAGE_NO_NETWORK_CONNECTION = "No connection to the network! "
            + "Please make sure you have network connected!";
    public static final String MESSAGE_BAD_RUNTIME = "You execute your code in a Java runtime"
            + " that does not support UTF-8!";
    public static final String MESSAGE_INVALID_INFO = "No loan contains both %1$s and %2$s!";
    public static final String MESSAGE_LOAN_IS_DONE = "You do not need to send reminder, because the loan is %s";
    public static final String MESSAGE_WRONG_OLDEMAIL = "The old email address is wrong!";
    public static final String MESSAGE_DUPLICATE_FAILURE = "The old email and the new email cannot be the same!";
    public static final String MESSAGE_INVALID_EMAIL = "Your new email address is invalid! It must be valid gmail!";
}

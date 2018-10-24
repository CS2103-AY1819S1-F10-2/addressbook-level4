package seedu.address.model.loan;

import java.util.function.Function;

/**
 * Represents a Loan's email in the loan book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email extends DataField<String> implements Censor {

    private static final String SPECIAL_CHARACTERS = "!#$%&'*+/=?`{|}~^.-";
    public static final String MESSAGE_EMAIL_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and adhere to the following constraints:\n"
            + "1. The local-part should only contain alphanumeric characters and these special characters, excluding "
            + "the parentheses, (" + SPECIAL_CHARACTERS + ") .\n"
            + "2. This is followed by a '@' and then a domain name. "
            + "The domain name must:\n"
            + "    - be at least 2 characters long\n"
            + "    - start and end with alphanumeric characters\n"
            + "    - consist of alphanumeric characters, a period or a hyphen for the characters in between, if any.";

    // alphanumeric and special characters
    private static final String LOCAL_PART_REGEX = "^[\\w" + SPECIAL_CHARACTERS + "]+";
    private static final String DOMAIN_FIRST_CHARACTER_REGEX = "[^\\W_]"; // alphanumeric characters except underscore
    private static final String DOMAIN_MIDDLE_REGEX = "[a-zA-Z0-9.-]*"; // alphanumeric, period and hyphen
    private static final String DOMAIN_LAST_CHARACTER_REGEX = "[^\\W_]$";

    /**
     * Constructs an {@code Email}.
     *
     * @param email A valid email address.
     */
    public Email(String email) {
        super(MESSAGE_EMAIL_CONSTRAINTS, Email::isValidEmail, Function.identity(), email);
    }

    /**
     * Returns if a given string is a valid email.
     */
    public static boolean isValidEmail(String objString) {
        return objString.matches(LOCAL_PART_REGEX + "@"
            + DOMAIN_FIRST_CHARACTER_REGEX + DOMAIN_MIDDLE_REGEX + DOMAIN_LAST_CHARACTER_REGEX);
    }

    @Override
    public String getCensored() {
        int index = this.value.indexOf('@');
        String censorPart = doCensoring(index);
        if (censorPart.length() == 0) {
            return this.value;
        } else {
            return this.value.charAt(0) + censorPart + this.value.substring(index - 2);
        }
    }

    @Override
    public String doCensoring(int length) {
        if (length <= 3) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length - 3; i++) {
                sb.append('x');
            }
            return sb.toString();
        }
    }
}

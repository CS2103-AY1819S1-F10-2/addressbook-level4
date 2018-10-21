package seedu.address.model.tag;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.loan.DataField;

/**
 * Represents a Tag in the loan book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag extends DataField<String> {

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags names should be alphanumeric";

    public static final Predicate<String> VALIDITY_PREDICATE =
        test -> test.matches("\\p{Alnum}+");

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        super(MESSAGE_TAG_CONSTRAINTS, VALIDITY_PREDICATE, Function.identity(), tagName);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String objString) {
        return VALIDITY_PREDICATE.test(objString);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + value + ']';
    }

}

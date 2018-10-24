package seedu.address.model.loan;

/**
 * The API of Censor.
 */
public interface Censor {

    /**
     * Returns the censored String.
     */
    public String getCensored();

    /**
     * Return censored part
     * @param length the length of the censored part
     */
    public String doCensoring(int length);
}

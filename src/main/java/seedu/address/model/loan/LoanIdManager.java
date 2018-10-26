package seedu.address.model.loan;

/**
 * The LoanIdManager class keeps track of the running Loan ID and
 * provides the next available ID when a new Loan is created.
 */
public class LoanIdManager {

    /** Represents the lack of a last used ID value. */
    private static final int NO_LAST_USED_ID_VALUE = -1;
    /** The first Loan ID to create. */
    private static final int INITIAL_ID_VALUE = 0;
    /** Represents when an ID Manager has no next available Loan ID. */
    private static final int MAXIMUM_ID_VALUE_REACHED = 999999999;

    private int lastUsedIdValue;
    private LoanId lastUsedLoanId;
    private boolean isMaximumReached;

    /**
     * Constructs a {@code LoanIdManager} that provides running Loan IDs starting from 0.
     */
    public LoanIdManager() {
        lastUsedIdValue = NO_LAST_USED_ID_VALUE;
        lastUsedLoanId = null;
        isMaximumReached = false;
    }

    /**
     * Constructs a {@code LoanIdManager} that provides running Loan IDs starting after
     * the specified last used Loan ID.
     *
     * @param lastUsedLoanId The last used Loan ID.
     */
    public LoanIdManager(LoanId lastUsedLoanId) {
        setFromLoanId(lastUsedLoanId);
    }

    /**
     * Sets the current state of this Loan ID Manager to match the state of the specified existing manager.
     */
    public void setFromExistingManager(LoanIdManager existingManager) {
        setFromLoanId(existingManager.getLastUsedLoanId());
    }

    /**
     * Checks if this Loan ID Manager has a next available Loan ID.
     *
     * @return true if this Loan ID Manager has a next available Loan ID.
     */
    public boolean hasNextAvailableLoanId() {
        return !isMaximumReached;
    }

    /**
     * Returns the next available Loan ID.
     *
     * @return The next available Loan ID.
     */
    public LoanId getNextAvailableLoanId() {
        if (isMaximumReached) {
            throw new IllegalStateException("No more available Loan IDs");
        }

        // Increment the running id value
        incrementLastUsedIdValue();

        // Create the output Loan ID
        LoanId output = LoanId.fromInt(lastUsedIdValue);
        lastUsedLoanId = output;

        // Update isMaximumReached if applicable
        if (output.isMaximumId()) {
            isMaximumReached = true;
            lastUsedIdValue = MAXIMUM_ID_VALUE_REACHED;
        }

        return output;
    }

    /**
     * Gets the last used Loan ID, if it exists.
     *
     * @return The last used Loan ID if it exists, null otherwise.
     */
    public LoanId getLastUsedLoanId() {
        return lastUsedLoanId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof LoanIdManager)) {
            return false;
        }

        LoanIdManager otherManager = (LoanIdManager) other;

        if (this.lastUsedIdValue != otherManager.lastUsedIdValue) {
            return false;
        }

        // If the lastUsedIdValue is equal, all the fields should be equal.
        if (this.lastUsedLoanId == null) {
            assert otherManager.lastUsedLoanId == null;
        } else {
            assert this.lastUsedLoanId.equals(otherManager.lastUsedLoanId);
        }
        assert this.isMaximumReached == otherManager.isMaximumReached;

        return true;
    }

    /**
     * Increment the last used ID value.
     */
    private void incrementLastUsedIdValue() {
        switch (lastUsedIdValue) {
        case NO_LAST_USED_ID_VALUE:
            // Set the value to the first possible Loan ID value.
            lastUsedIdValue = INITIAL_ID_VALUE;
            return;
        case MAXIMUM_ID_VALUE_REACHED:
            throw new IllegalStateException("Attempt to increment the last used ID value when it has reached the "
                    + "maximum possible value");
        default:
            // Increment the integer value.
            ++lastUsedIdValue;
            break;
        }
    }

    /**
     * Set the current state of this Loan ID Manager from the specified last used Loan ID.
     */
    private void setFromLoanId(LoanId lastUsedLoanId) {
        if (lastUsedLoanId == null) {
            isMaximumReached = false;
            this.lastUsedLoanId = null;
            lastUsedIdValue = NO_LAST_USED_ID_VALUE;

            return;
        } else if (lastUsedLoanId.isMaximumId()) {
            isMaximumReached = true;
            this.lastUsedLoanId = lastUsedLoanId;
            lastUsedIdValue = MAXIMUM_ID_VALUE_REACHED;

            return;
        }

        isMaximumReached = false;
        this.lastUsedLoanId = lastUsedLoanId;
        lastUsedIdValue = lastUsedLoanId.value;
    }
}

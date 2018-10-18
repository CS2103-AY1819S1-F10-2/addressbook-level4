package seedu.address.model.loan;

/**
 * Represents a LoanStatus in the LoanBook.
 */
public enum LoanStatus {
    ONGOING {
        public String toString() {
            return "Ongoing";
        }
    },
    RETURNED {
        public String toString() {
            return "Returned";
        }
    },
    DELETED {
        public String toString() {
            return "Deleted";
        }
    }
}

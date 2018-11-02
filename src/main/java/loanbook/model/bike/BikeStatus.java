package loanbook.model.bike;

/**
 * Represents the status of a bike in the loan book.
 */
public enum BikeStatus {
    AVAILABLE {
        public String toString() {
            return "Available";
        }
    },
    LOANED_OUT {
        public String toString() {
            return "Loaned Out";
        }
    }
}

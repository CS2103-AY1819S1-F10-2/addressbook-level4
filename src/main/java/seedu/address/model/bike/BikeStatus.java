package seedu.address.model.bike;

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

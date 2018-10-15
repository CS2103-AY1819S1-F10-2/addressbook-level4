package seedu.address.model.bike;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.loan.Name;

/**
 * Represents a Bike in the loan book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Bike {

    //Identity fields
    /**
     * The name of the Bike also doubles as its ID, i.e. it should be unique as it is used to identify this bike.
     * For now, borrow the Name class from Loan. It can be its own separate class once the difference matters.
     */
    private final Name name;

    // Data fields
    private final BikeStatus status;

    /**
     * Every field must be present and not null.
     */
    public Bike(Name name) {
        requireAllNonNull(name);
        this.name = name;

        this.status = BikeStatus.AVAILABLE;
    }

    public Name getName() {
        return name;
    }

    public BikeStatus getStatus() {
        return status;
    }

    public boolean isAvailable() {
        return this.getStatus().equals(BikeStatus.AVAILABLE);
    }

    /**
     * Returns true iff both bikes have the same name.
     * This defines a weaker notion of equality between two bikes.
     */
    public boolean isSameBike(Bike otherBike) {
        if (otherBike == this) {
            return true;
        }

        return otherBike != null
            && otherBike.getName().equals(this.getName());
    }

    /**
     * Returns true if both bikes have the same identity and data fields.
     * This defines a stronger notion of equality between two bikes.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Bike)) {
            return false;
        }

        Bike otherBike = (Bike) other;
        return otherBike.getName().equals(getName())
            && otherBike.getStatus().equals(getStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
            .append(" Status: ").append(getStatus());
        return builder.toString();
    }
}

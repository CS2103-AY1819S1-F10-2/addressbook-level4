package seedu.address.testutil;

import seedu.address.model.bike.Bike;
import seedu.address.model.loan.Name;

/**
 * A utility class to help with building Bike objects.
 */
public class BikeBuilder {
    public static final String DEFAULT_NAME = "BIKE001";

    private Name name;

    public BikeBuilder() {
        name = new Name(DEFAULT_NAME);
    }

    /**
     * Initializes the BikeBuilder with the data of {@code bikeToCopy}.
     */
    public BikeBuilder(Bike bikeToCopy) {
        name = bikeToCopy.getName();
    }

    /**
     * Sets the {@code Name} of the {@code Bike} that we are building.
     */
    public BikeBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    public Bike build() {
        return new Bike(name);
    }
}

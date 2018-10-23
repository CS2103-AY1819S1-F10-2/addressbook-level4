package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.bike.Bike;

/**
 * A utility class containing a list of {@code Bike} objects to be used in tests.
 */
public class TypicalBikes {

    public static final Bike BIKE1 = new BikeBuilder().withName("BIKE001").build();
    public static final Bike BIKE2 = new BikeBuilder().withName("BIKE002").build();
    public static final Bike BIKE3 = new BikeBuilder().withName("Silver Surfer").build();
    public static final Bike BIKE4 = new BikeBuilder().withName("Blue Ocean").build();

    public static List<Bike> getTypicalBikes() {
        return new ArrayList<>(Arrays.asList(BIKE1, BIKE2, BIKE3, BIKE4));
    }
}

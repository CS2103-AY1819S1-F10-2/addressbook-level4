package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;

import seedu.address.model.bike.Bike;

/**
 * A utility class containing a list of {@code Bike} objects to be used in tests.
 */
public class TypicalBikes {

    public static final Bike BIKE1 = new BikeBuilder().withName(VALID_NAME_BIKE1).build();
    public static final Bike BIKE2 = new BikeBuilder().withName(VALID_NAME_BIKE2).build();
}

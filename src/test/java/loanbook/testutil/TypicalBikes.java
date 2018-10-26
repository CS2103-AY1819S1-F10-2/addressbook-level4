package loanbook.testutil;

import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE1;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE2;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE3;
import static loanbook.logic.commands.CommandTestUtil.VALID_NAME_BIKE4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import loanbook.model.bike.Bike;

/**
 * A utility class containing a list of {@code Bike} objects to be used in tests.
 */
public class TypicalBikes {

    public static final Bike BIKE1 = new BikeBuilder().withName(VALID_NAME_BIKE1).build();
    public static final Bike BIKE2 = new BikeBuilder().withName(VALID_NAME_BIKE2).build();
    public static final Bike BIKE3 = new BikeBuilder().withName(VALID_NAME_BIKE3).build();
    public static final Bike BIKE4 = new BikeBuilder().withName(VALID_NAME_BIKE4).build();

    public static List<Bike> getTypicalBikes() {
        return new ArrayList<>(Arrays.asList(BIKE1, BIKE2, BIKE3, BIKE4));
    }
}

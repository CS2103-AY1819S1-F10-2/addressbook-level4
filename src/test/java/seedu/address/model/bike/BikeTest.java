package seedu.address.model.bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBikes.BIKE1;
import static seedu.address.testutil.TypicalBikes.BIKE2;

import org.junit.Test;

import seedu.address.testutil.BikeBuilder;

public class BikeTest {

    @Test
    public void isSameBike() {
        // same object -> returns true
        assertTrue(BIKE1.isSame(BIKE1));

        // null -> returns false
        assertFalse(BIKE1.isSame(null));

        // different name -> returns false
        Bike editedBike1 = new BikeBuilder(BIKE1).withName("B002").build();
        assertFalse(BIKE1.isSame(editedBike1));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Bike bike1Copy = new BikeBuilder(BIKE1).build();
        assertTrue(BIKE1.equals(bike1Copy));

        // same object -> returns true
        assertTrue(BIKE1.equals(BIKE1));

        // null -> returns false
        assertFalse(BIKE1 == null);

        // different type -> returns false
        assertFalse(BIKE1.equals(5));

        // different bike -> returns false
        assertFalse(BIKE1.equals(BIKE2));

        // different name -> returns false
        Bike editedBike1 = new BikeBuilder(BIKE1).withName("B002").build();
        assertFalse(BIKE1.equals(editedBike1));
    }

    @Test
    public void toStringTest() {
        assertEquals(BIKE1.toString(), "BIKE001 Status: Available");
    }
}

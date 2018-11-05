package loanbook.storage;

import static loanbook.storage.XmlAdaptedBike.MISSING_FIELD_MESSAGE_FORMAT;
import static loanbook.testutil.TypicalBikes.BIKE1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.model.loan.Name;
import loanbook.testutil.Assert;

public class XmlAdaptedBikeTest {
    private static final String INVALID_NAME = "Not@Bike";

    private static final String VALID_NAME = BIKE1.getName().toString();

    @Test
    public void toModelTypeValidBikeDetails_returnsBike() throws Exception {
        XmlAdaptedBike bike = new XmlAdaptedBike(BIKE1);
        assertEquals(BIKE1, bike.toModelType());
    }

    @Test
    public void toModelTypeInvalidNameThrowsIllegalValueException() {
        XmlAdaptedBike bike = new XmlAdaptedBike(INVALID_NAME);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, bike::toModelType);
    }

    @Test
    public void toModelTypeNullNameThrowsIllegalValueException() {
        XmlAdaptedBike bike = new XmlAdaptedBike((String) null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, bike::toModelType);
    }

    @Test
    public void equalityTest() {
        XmlAdaptedBike bike1 = new XmlAdaptedBike("IsABike");
        XmlAdaptedBike bike2 = new XmlAdaptedBike("IsABike");
        XmlAdaptedBike bike3 = new XmlAdaptedBike("NotABike");

        assertEquals(bike1, bike1); // Same instance
        assertEquals(bike1, bike2); // Same value
        assertNotEquals(bike1, bike3); // Different value
        assertNotEquals(bike1, "Different type");
    }
}

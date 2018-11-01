package loanbook.ui;

import static loanbook.ui.testutil.GuiTestAssert.assertCardDisplaysBike;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import guitests.guihandles.BikeCardHandle;
import loanbook.model.bike.Bike;
import loanbook.testutil.BikeBuilder;

public class BikeCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Bike bike = new BikeBuilder().build();
        BikeCard bikeCard = new BikeCard(bike, 1);
        uiPartRule.setUiPart(bikeCard);
        assertCardDisplay(bikeCard, bike, 1);
    }

    @Test
    public void equals() {
        Bike bike = new BikeBuilder().build();
        BikeCard bikeCard = new BikeCard(bike, 0);

        // same bike, same index -> returns true
        BikeCard copy = new BikeCard(bike, 0);
        assertTrue(bikeCard.equals(copy));

        // same object -> returns true
        assertTrue(bikeCard.equals(bikeCard));

        // null -> returns false
        assertFalse(bikeCard == null);

        // different types -> returns false
        assertFalse(bikeCard.equals(0));

        // different bike, same index -> returns false
        Bike differentBike = new BikeBuilder().withName("differentName").build();
        assertFalse(bikeCard.equals(new BikeCard(differentBike, 0)));

        // same bike, different index -> returns false
        assertFalse(bikeCard.equals(new BikeCard(bike, 1)));
    }

    /**
     * Asserts that {@code bikeCard} displays the details of {@code expectedBike} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BikeCard bikeCard, Bike expectedBike, int expectedId) {
        guiRobot.pauseForHuman();

        BikeCardHandle bikeCardHandle = new BikeCardHandle(bikeCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId), bikeCardHandle.getId());

        // verify bike details are displayed correctly
        assertCardDisplaysBike(expectedBike, bikeCardHandle);
    }
}

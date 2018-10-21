package seedu.address.model.bike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalBikes.BIKE1;
import static seedu.address.testutil.TypicalBikes.BIKE2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.bike.exceptions.BikeNotFoundException;
import seedu.address.model.bike.exceptions.DuplicateBikeException;

public class UniqueBikeListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueBikeList uniqueBikeList = new UniqueBikeList();

    @Test
    public void containsNullBikeThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.contains(null);
    }

    @Test
    public void containsBikeNotInListReturnsFalse() {
        assertFalse(uniqueBikeList.contains(BIKE1));
    }

    @Test
    public void containsBikeInListReturnsTrue() {
        uniqueBikeList.add(BIKE1);
        assertTrue(uniqueBikeList.contains(BIKE1));
    }

    @Test
    public void addNullBikeThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.add(null);
    }

    @Test
    public void addDuplicateBikeThrowsDuplicateBikeException() {
        uniqueBikeList.add(BIKE1);
        thrown.expect(DuplicateBikeException.class);
        uniqueBikeList.add(BIKE1);
    }

    @Test
    public void setBikeNullTargetBikeThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.setBike(null, BIKE1);
    }

    @Test
    public void setBikeNullEditedBikeThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.setBike(BIKE1, null);
    }

    @Test
    public void setBikeTargetBikeNotInListThrowsBikeNotFoundException() {
        thrown.expect(BikeNotFoundException.class);
        uniqueBikeList.setBike(BIKE1, BIKE1);
    }

    @Test
    public void setBikeEditedBikeIsSameBike_success() {
        uniqueBikeList.add(BIKE1);
        uniqueBikeList.setBike(BIKE1, BIKE1);
        UniqueBikeList expectedUniqueBikeList = new UniqueBikeList();
        expectedUniqueBikeList.add(BIKE1);
        assertEquals(expectedUniqueBikeList, uniqueBikeList);
    }

    @Test
    public void setBikeEditedBikeHasDifferentIdentity_success() {
        uniqueBikeList.add(BIKE1);
        uniqueBikeList.setBike(BIKE1, BIKE2);
        UniqueBikeList expectedUniqueBikeList = new UniqueBikeList();
        expectedUniqueBikeList.add(BIKE2);
        assertEquals(expectedUniqueBikeList, uniqueBikeList);
    }

    @Test
    public void setBikeEditedBikeHasNonUniqueIdentityThrowsDuplicateBikeException() {
        uniqueBikeList.add(BIKE1);
        uniqueBikeList.add(BIKE2);
        thrown.expect(DuplicateBikeException.class);
        uniqueBikeList.setBike(BIKE1, BIKE2);
    }

    @Test
    public void removeNullBikeThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.remove(null);
    }

    @Test
    public void removeBikeDoesNotExistThrowsBikeNotFoundException() {
        thrown.expect(BikeNotFoundException.class);
        uniqueBikeList.remove(BIKE1);
    }

    @Test
    public void remove_existingBikeRemovesBike() {
        uniqueBikeList.add(BIKE1);
        uniqueBikeList.remove(BIKE1);
        UniqueBikeList expectedUniqueBikeList = new UniqueBikeList();
        assertEquals(expectedUniqueBikeList, uniqueBikeList);
    }

    @Test
    public void setBikesNullUniqueBikeListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.setBikes((UniqueBikeList) null);
    }

    @Test
    public void setBikesUniqueBikeListReplacesOwnListWithProvidedUniqueBikeList() {
        uniqueBikeList.add(BIKE1);
        UniqueBikeList expectedUniqueBikeList = new UniqueBikeList();
        expectedUniqueBikeList.add(BIKE2);
        uniqueBikeList.setBikes(expectedUniqueBikeList);
        assertEquals(expectedUniqueBikeList, uniqueBikeList);
    }

    @Test
    public void setBikesNullListThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueBikeList.setBikes((List<Bike>) null);
    }

    @Test
    public void setBikesListReplacesOwnListWithProvidedList() {
        uniqueBikeList.add(BIKE1);
        List<Bike> bikeList = Collections.singletonList(BIKE2);
        uniqueBikeList.setBikes(bikeList);
        UniqueBikeList expectedUniqueBikeList = new UniqueBikeList();
        expectedUniqueBikeList.add(BIKE2);
        assertEquals(expectedUniqueBikeList, uniqueBikeList);
    }

    @Test
    public void setBikesListWithDuplicateBikesThrowsDuplicateBikeException() {
        List<Bike> listWithDuplicateBikes = Arrays.asList(BIKE1, BIKE1);
        thrown.expect(DuplicateBikeException.class);
        uniqueBikeList.setBikes(listWithDuplicateBikes);
    }

    @Test
    public void asUnmodifiableObservableListModifyListThrowsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueBikeList.asUnmodifiableObservableList().remove(0);
    }
}

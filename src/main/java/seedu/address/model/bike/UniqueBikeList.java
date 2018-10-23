package seedu.address.model.bike;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.bike.exceptions.BikeNotFoundException;
import seedu.address.model.bike.exceptions.DuplicateBikeException;

/**
 * A list of bikes that enforces uniqueness between its elements and does not allow nulls.
 * A bike is considered unique by comparing using {@code Bike#isSameBike(Bike)}. As such, adding and updating of
 * bikes uses Bike#isSameBike(Bike) for equality so as to ensure that the bike being added or updated is
 * unique in terms of identity in the UniqueBikeList. However, the removal of a bike uses Bike#equals(Object) so
 * as to ensure that the bike with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Bike#isSameBike(Bike)
 */
public class UniqueBikeList implements Iterable<Bike> {

    private final ObservableList<Bike> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent bike as the given argument.
     */
    public boolean contains(Bike toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameBike);
    }

    /**
     * Adds a bike to the list.
     * The bike must not already exist in the list.
     */
    public void add(Bike toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateBikeException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the bike {@code target} in the list with {@code editedBike}.
     * {@code target} must exist in the list.
     * The bike identity of {@code editedBike} must not be the same as another existing bike in the list.
     */
    public void setBike(Bike target, Bike editedBike) {
        requireAllNonNull(target, editedBike);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new BikeNotFoundException();
        }

        if (!target.isSameBike(editedBike) && contains(editedBike)) {
            throw new DuplicateBikeException();
        }

        internalList.set(index, editedBike);
    }

    /**
     * Removes the equivalent bike from the list.
     * The bike must exist in the list.
     */
    public void remove(Bike toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new BikeNotFoundException();
        }
    }

    public void setBikes(UniqueBikeList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code bikes}.
     * {@code bikes} must not contain duplicate bikes.
     */
    public void setBikes(List<Bike> bikes) {
        requireAllNonNull(bikes);
        if (!bikesAreUnique(bikes)) {
            throw new DuplicateBikeException();
        }

        internalList.setAll(bikes);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Bike> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Bike> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueBikeList // instanceof handles nulls
            && internalList.equals(((UniqueBikeList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code bikes} contains only unique bikes.
     */
    private boolean bikesAreUnique(List<Bike> bikes) {
        for (int i = 0; i < bikes.size() - 1; i++) {
            for (int j = i + 1; j < bikes.size(); j++) {
                if (bikes.get(i).isSameBike(bikes.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}


package seedu.address.model.bike;

import seedu.address.model.UniqueList;
import seedu.address.model.bike.exceptions.BikeNotFoundException;
import seedu.address.model.bike.exceptions.DuplicateBikeException;

/**
 * A UniqueList of bikes.
 */
public class UniqueBikeList extends UniqueList<Bike> {

    protected void throwDuplicateException() throws DuplicateBikeException {
        throw new DuplicateBikeException();
    }

    protected void throwNotFoundException() throws BikeNotFoundException {
        throw new BikeNotFoundException();
    }
}


package loanbook.model.bike;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import loanbook.model.UniqueList;
import loanbook.model.bike.exceptions.BikeNotFoundException;
import loanbook.model.bike.exceptions.DuplicateBikeException;

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

    /**
     * Returns a bike in the list whose name matches the given name.
     *
     * @param bikeName The name of the bike to search for.
     * @return An Optional Bike whose name matches the given name, or Optional.empty() if no match is found.
     */
    public Optional<Bike> getBike(String bikeName) {
        requireNonNull(bikeName);
        return internalList.stream()
                .filter(bike -> bike.getName().value.equals(bikeName))
                .findFirst();
    }
}


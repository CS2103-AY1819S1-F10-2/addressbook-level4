package loanbook.model;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list that enforces uniqueness between its elements and does not allow nulls.
 * An item is considered unique by comparing using {@code T#isSame(T)}. As such, adding and updating of
 * items uses T#isSame(T) for equality so as to ensure that the item being added or updated is
 * unique in terms of identity in the UniqueList. However, the removal of an item uses T#equals(Object) so
 * as to ensure that the item with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @param <T> The type of item in the list.
 * @see T#isSame(T)
 */
public abstract class UniqueList<T extends UniqueListItem<T>> implements Iterable<T> {

    protected final ObservableList<T> internalList = FXCollections.observableArrayList();

    /**
     * Throws an exception signifying that a duplicate item has been detected.
     */
    protected abstract void throwDuplicateException() throws RuntimeException;

    /**
     * Throws an exception signifying that a specified item cannot be found.
     */
    protected abstract void throwNotFoundException() throws RuntimeException;

    /**
     * Returns true if the list contains an equivalent item as the given argument.
     */
    public boolean contains(T toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSame);
    }

    /**
     * Adds an item to the list.
     * The item must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throwDuplicateException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the item {@code target} in the list with {@code editedItem}.
     * {@code target} must exist in the list.
     * The {@code editedItem} must not be identical to another existing item in the list.
     */
    public void set(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throwNotFoundException();
        }

        if (!target.isSame(editedItem) && contains(editedItem)) {
            throwDuplicateException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent item from the list.
     * The item must exist in the list.
     */
    public void remove(T toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throwNotFoundException();
        }
    }

    /**
     * Replaces the current list of items with the replacement.
     * @param replacement A new list of items.
     */
    public void setAll(UniqueList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code items}.
     * {@code items} must not contain duplicate items.
     */
    public void setAll(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throwDuplicateException();
        }

        internalList.setAll(items);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; // short circuit if same object
        }
        if (other == null) {
            return false; // null objects are not equal to this object (which is non-null)
        }
        // Returns true if both objects are of the same class and holding the same value
        return other.getClass() == this.getClass()
            && this.internalList.equals(((UniqueList) other).internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique items.
     */
    protected boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSame(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

package loanbook.model;

/**
 * An item that a {@code UniqueList} can implement.
 * @param <T> The item type.
 */
public interface UniqueListItem<T extends UniqueListItem<T>> {

    /**
     * Checks if both items are weakly identical. A UniqueList should not have two weakly identical items.
     * "Weakly identical" is opposed to {@code equals()} because we may not want items to be exactly the same
     * to be considered unique.
     *
     * @param other The other item to compare against this item.
     * @return true iff both items are weakly identical.
     */
    boolean isSame(T other);
}

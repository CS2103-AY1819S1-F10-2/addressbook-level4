package loanbook.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

/**
 * Utility methods related to Collections
 */
public class CollectionUtil {

    /** @see #requireAllNonNull(Collection) */
    public static void requireAllNonNull(Object... items) {
        requireNonNull(items);
        Stream.of(items).forEach(Objects::requireNonNull);
    }

    /**
     * Throws NullPointerException if {@code items} or any element of {@code items} is null.
     */
    public static void requireAllNonNull(Collection<?> items) {
        requireNonNull(items);
        items.forEach(Objects::requireNonNull);
    }

    /**
     * Returns true if {@code items} contain any elements that are non-null.
     */
    public static boolean isAnyNonNull(Object... items) {
        return items != null && Arrays.stream(items).anyMatch(Objects::nonNull);
    }

    /**
     * Tests each element in the first iterable collection with the corresponding element in the second
     * iterable collection.
     *
     * @return true if the two iterable collections are of equal sizes and all tests pass.
     */
    public static <T> boolean testByElement(Iterable<T> first, Iterable<T> second, BiPredicate<T, T> predicate) {
        Iterator<T> firstIterator = first.iterator();
        Iterator<T> secondIterator = second.iterator();

        while (firstIterator.hasNext()) {
            if (!secondIterator.hasNext()) {
                // First iterator has more elements than second.
                return false;
            }

            if (!predicate.test(firstIterator.next(), secondIterator.next())) {
                return false;
            }
        }

        // If second iterator has remaining elements, then the sizes
        // are not equal and hence should return false.
        //
        // Otherwise, the two iterators are equal.
        return !secondIterator.hasNext();
    }
}

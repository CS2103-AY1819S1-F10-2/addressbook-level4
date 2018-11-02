package loanbook.model.bike.exceptions;

/**
 * Signals that the operation will result in duplicate Bikes (Bikes are considered duplicates if they have the same
 * identity).
 */
public class DuplicateBikeException extends RuntimeException {
    public DuplicateBikeException() {
        super("Operation would result in duplicate bikes");
    }
}

package loanbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import loanbook.commons.exceptions.DataConversionException;
import loanbook.model.LoanBook;
import loanbook.model.ReadOnlyLoanBook;

/**
 * Represents a storage for {@link LoanBook}.
 */
public interface LoanBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getLoanBookFilePath();

    /**
     * Returns LoanBook data as a {@link ReadOnlyLoanBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyLoanBook> readLoanBook() throws DataConversionException, IOException;

    /**
     * @see #getLoanBookFilePath()
     */
    Optional<ReadOnlyLoanBook> readLoanBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyLoanBook} to the storage.
     * @param loanBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveLoanBook(ReadOnlyLoanBook loanBook) throws IOException;

    /**
     * @see #saveLoanBook(ReadOnlyLoanBook)
     */
    void saveLoanBook(ReadOnlyLoanBook loanBook, Path filePath) throws IOException;

}

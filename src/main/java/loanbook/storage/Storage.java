package loanbook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import loanbook.commons.events.model.LoanBookChangedEvent;
import loanbook.commons.events.storage.DataSavingExceptionEvent;
import loanbook.commons.exceptions.DataConversionException;
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends LoanBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    Path getLoanBookFilePath();

    @Override
    Optional<ReadOnlyLoanBook> readLoanBook() throws DataConversionException, IOException;

    @Override
    void saveLoanBook(ReadOnlyLoanBook loanBook) throws IOException;

    /**
     * Saves the current version of the Loan Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleLoanBookChangedEvent(LoanBookChangedEvent abce);
}

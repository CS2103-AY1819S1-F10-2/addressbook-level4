package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.LoanBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyLoanBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of LoanBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private LoanBookStorage loanBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(LoanBookStorage loanBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.loanBookStorage = loanBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ LoanBook methods ==============================

    @Override
    public Path getLoanBookFilePath() {
        return loanBookStorage.getLoanBookFilePath();
    }

    @Override
    public Optional<ReadOnlyLoanBook> readLoanBook() throws DataConversionException, IOException {
        return readLoanBook(loanBookStorage.getLoanBookFilePath());
    }

    @Override
    public Optional<ReadOnlyLoanBook> readLoanBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return loanBookStorage.readLoanBook(filePath);
    }

    @Override
    public void saveLoanBook(ReadOnlyLoanBook loanBook) throws IOException {
        saveLoanBook(loanBook, loanBookStorage.getLoanBookFilePath());
    }

    @Override
    public void saveLoanBook(ReadOnlyLoanBook loanBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        loanBookStorage.saveLoanBook(loanBook, filePath);
    }


    @Override
    @Subscribe
    public void handleLoanBookChangedEvent(LoanBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveLoanBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}

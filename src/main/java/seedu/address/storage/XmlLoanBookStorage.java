package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.ReadOnlyLoanBook;

/**
 * A class to access LoanBook data stored as an xml file on the hard disk.
 */
public class XmlLoanBookStorage implements LoanBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlLoanBookStorage.class);

    private Path filePath;

    public XmlLoanBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLoanBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyLoanBook> readLoanBook() throws DataConversionException, IOException {
        return readLoanBook(filePath);
    }

    /**
     * Similar to {@link #readLoanBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyLoanBook> readLoanBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("LoanBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableLoanBook xmlLoanBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlLoanBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveLoanBook(ReadOnlyLoanBook loanBook) throws IOException {
        saveLoanBook(loanBook, filePath);
    }

    /**
     * Similar to {@link #saveLoanBook(ReadOnlyLoanBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveLoanBook(ReadOnlyLoanBook loanBook, Path filePath) throws IOException {
        requireNonNull(loanBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableLoanBook(loanBook));
    }

}

package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalLoanBook.getTypicalLoanBook;
import static seedu.address.testutil.TypicalLoans.ALICE;
import static seedu.address.testutil.TypicalLoans.HOON;
import static seedu.address.testutil.TypicalLoans.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.LoanBook;
import seedu.address.model.ReadOnlyLoanBook;

public class XmlLoanBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlLoanBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readLoanBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readLoanBook(null);
    }

    private java.util.Optional<ReadOnlyLoanBook> readLoanBook(String filePath) throws Exception {
        return new XmlLoanBookStorage(Paths.get(filePath)).readLoanBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readLoanBook("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readLoanBook("NotXmlFormatLoanBook.xml");

        /* IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
         * That means you should not have more than one exception test in one method
         */
    }

    @Test
    public void readLoanBook_invalidLoanLoanBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLoanBook("invalidLoanLoanBook.xml");
    }

    @Test
    public void readLoanBook_invalidAndValidLoanLoanBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readLoanBook("invalidAndValidLoanLoanBook.xml");
    }

    @Test
    public void readAndSaveLoanBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempLoanBook.xml");
        LoanBook original = getTypicalLoanBook();
        XmlLoanBookStorage xmlLoanBookStorage = new XmlLoanBookStorage(filePath);

        //Save in new file and read back
        xmlLoanBookStorage.saveLoanBook(original, filePath);
        ReadOnlyLoanBook readBack = xmlLoanBookStorage.readLoanBook(filePath).get();
        assertEquals(original, new LoanBook(readBack));

        //Modify data, overwrite exiting file, and read back
        original.addLoan(HOON);
        original.removeLoan(ALICE);
        xmlLoanBookStorage.saveLoanBook(original, filePath);
        readBack = xmlLoanBookStorage.readLoanBook(filePath).get();
        assertEquals(original, new LoanBook(readBack));

        //Save and read without specifying file path
        original.addLoan(IDA);
        xmlLoanBookStorage.saveLoanBook(original); //file path not specified
        readBack = xmlLoanBookStorage.readLoanBook().get(); //file path not specified
        assertEquals(original, new LoanBook(readBack));

    }

    @Test
    public void saveLoanBook_nullLoanBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLoanBook(null, "SomeFile.xml");
    }

    /**
     * Saves {@code loanBook} at the specified {@code filePath}.
     */
    private void saveLoanBook(ReadOnlyLoanBook loanBook, String filePath) {
        try {
            new XmlLoanBookStorage(Paths.get(filePath))
                    .saveLoanBook(loanBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveLoanBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveLoanBook(new LoanBook(), null);
    }


}

package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.LoanBook;
import seedu.address.testutil.TypicalLoanBook;

public class XmlSerializableLoanBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableLoanBookTest");
    private static final Path TYPICAL_LOANBOOK_FILE = TEST_DATA_FOLDER.resolve("typicalLoanBook.xml");
    private static final Path INVALID_BIKE_FILE = TEST_DATA_FOLDER.resolve("invalidBikeLoanBook.xml");
    private static final Path INVALID_LOAN_FILE = TEST_DATA_FOLDER.resolve("invalidLoanLoanBook.xml");
    private static final Path INVALID_LOANSTATUS_FILE = TEST_DATA_FOLDER.resolve("invalidLoanStatusLoanBook.xml");
    private static final Path DUPLICATE_BIKE_FILE = TEST_DATA_FOLDER.resolve("duplicateBikeLoanBook.xml");
    private static final Path DUPLICATE_LOAN_FILE = TEST_DATA_FOLDER.resolve("duplicateLoanLoanBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalLoansFile_success() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_LOANBOOK_FILE,
            XmlSerializableLoanBook.class);
        LoanBook loanBookFromFile = dataFromFile.toModelType();
        LoanBook typicalLoansLoanBook = TypicalLoanBook.getTypicalLoanBook();
        assertEquals(loanBookFromFile, typicalLoansLoanBook);
    }

    @Test
    public void toModelType_invalidBikeFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(INVALID_BIKE_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidLoanFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(INVALID_LOAN_FILE,
            XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateBikes_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_BIKE_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableLoanBook.MESSAGE_DUPLICATE_BIKE);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidLoanStatusFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(INVALID_LOANSTATUS_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateLoans_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_LOAN_FILE,
            XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableLoanBook.MESSAGE_DUPLICATE_LOAN);
        dataFromFile.toModelType();
    }

}

package loanbook.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.commons.exceptions.IllegalValueException;
import loanbook.commons.util.XmlUtil;
import loanbook.model.LoanBook;
import loanbook.testutil.TypicalLoanBook;

public class XmlSerializableLoanBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableLoanBookTest");

    private static final Path DUPLICATE_BIKE_FILE = TEST_DATA_FOLDER.resolve("duplicateBikeLoanBook.xml");
    private static final Path DUPLICATE_LOAN_ID_FILE = TEST_DATA_FOLDER.resolve("duplicateLoanIdLoanBook.xml");
    private static final Path ILLEGAL_STATE_ID_MANAGER_FILE =
            TEST_DATA_FOLDER.resolve("illegalStateLoanIdManagerLoanBook.xml");
    private static final Path INVALID_BIKE_FILE = TEST_DATA_FOLDER.resolve("invalidBikeLoanBook.xml");
    private static final Path INVALID_LOAN_FILE = TEST_DATA_FOLDER.resolve("invalidLoanLoanBook.xml");
    private static final Path INVALID_LOANSTATUS_FILE = TEST_DATA_FOLDER.resolve("invalidLoanStatusLoanBook.xml");
    private static final Path INVALID_LOAN_ID_MANAGER_FILE =
            TEST_DATA_FOLDER.resolve("invalidLoanIdManagerLoanBook.xml");
    private static final Path TYPICAL_LOANBOOK_FILE = TEST_DATA_FOLDER.resolve("typicalLoanBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalLoansFile_success() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_LOANBOOK_FILE,
            XmlSerializableLoanBook.class);
        LoanBook loanBookFromFile = dataFromFile.toModelType();
        LoanBook typicalLoanBook = TypicalLoanBook.getTypicalLoanBook();
        assertEquals(loanBookFromFile, typicalLoanBook);
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
    public void toModelType_invalidLoanIdManager_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(INVALID_LOAN_ID_MANAGER_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateLoanId_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(DUPLICATE_LOAN_ID_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableLoanBook.MESSAGE_DUPLICATE_LOAN_ID);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_illegalStateLoanIdManager_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(ILLEGAL_STATE_ID_MANAGER_FILE,
                XmlSerializableLoanBook.class);
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(XmlSerializableLoanBook.MESSAGE_ILLEGAL_LOAN_ID_MANAGER);
        dataFromFile.toModelType();

    }

}

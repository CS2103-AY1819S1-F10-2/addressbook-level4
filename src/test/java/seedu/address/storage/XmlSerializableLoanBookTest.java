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
import seedu.address.testutil.TypicalLoans;

public class XmlSerializableLoanBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlSerializableLoanBookTest");
    private static final Path TYPICAL_LOANS_FILE = TEST_DATA_FOLDER.resolve("typicalLoansLoanBook.xml");
    private static final Path INVALID_LOAN_FILE = TEST_DATA_FOLDER.resolve("invalidLoanLoanBook.xml");
    private static final Path DUPLICATE_LOAN_FILE = TEST_DATA_FOLDER.resolve("duplicateLoanLoanBook.xml");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalLoansFile_success() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(TYPICAL_LOANS_FILE,
                XmlSerializableLoanBook.class);
        LoanBook loanBookFromFile = dataFromFile.toModelType();
        LoanBook typicalLoansLoanBook = TypicalLoans.getTypicalLoanBook();
        assertEquals(loanBookFromFile, typicalLoansLoanBook);
    }

    @Test
    public void toModelType_invalidLoanFile_throwsIllegalValueException() throws Exception {
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(INVALID_LOAN_FILE,
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

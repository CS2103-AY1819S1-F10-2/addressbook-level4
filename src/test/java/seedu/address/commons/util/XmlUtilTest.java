package seedu.address.commons.util;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.LoanBook;
import seedu.address.storage.XmlAdaptedLoan;
import seedu.address.storage.XmlAdaptedTag;
import seedu.address.storage.XmlSerializableLoanBook;
import seedu.address.testutil.LoanBookBuilder;
import seedu.address.testutil.LoanBuilder;
import seedu.address.testutil.TestUtil;

public class XmlUtilTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "XmlUtilTest");
    private static final Path EMPTY_FILE = TEST_DATA_FOLDER.resolve("empty.xml");
    private static final Path MISSING_FILE = TEST_DATA_FOLDER.resolve("missing.xml");
    private static final Path VALID_FILE = TEST_DATA_FOLDER.resolve("validLoanBook.xml");
    private static final Path MISSING_LOAN_FIELD_FILE = TEST_DATA_FOLDER.resolve("missingLoanField.xml");
    private static final Path INVALID_LOAN_FIELD_FILE = TEST_DATA_FOLDER.resolve("invalidLoanField.xml");
    private static final Path VALID_LOAN_FILE = TEST_DATA_FOLDER.resolve("validLoan.xml");
    private static final Path TEMP_FILE = TestUtil.getFilePathInSandboxFolder("tempLoanBook.xml");

    private static final String INVALID_PHONE = "9482asf424";

    private static final String VALID_NAME = "Hans Muster";
    private static final String VALID_NRIC = "T0331476B";
    private static final String VALID_PHONE = "9482424";
    private static final String VALID_EMAIL = "hans@example";
    private static final String VALID_ADDRESS = "4th street";
    private static final String VALID_BIKE = "BIKE001";
    private static final String VALID_LOANRATE = "30.6";
    private static final String VALID_LOANTIMEA = "2018-01-01 10:10";
    private static final String VALID_LOANTIMEB = "2018-01-01 17:10";
    private static final String VALID_LOANSTATUS = "ONGOING";
    private static final List<XmlAdaptedTag> VALID_TAGS = Collections.singletonList(new XmlAdaptedTag("friends"));

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getDataFromFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(null, LoanBook.class);
    }

    @Test
    public void getDataFromFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.getDataFromFile(VALID_FILE, null);
    }

    @Test
    public void getDataFromFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.getDataFromFile(MISSING_FILE, LoanBook.class);
    }

    @Test
    public void getDataFromFile_emptyFile_dataFormatMismatchException() throws Exception {
        thrown.expect(JAXBException.class);
        XmlUtil.getDataFromFile(EMPTY_FILE, LoanBook.class);
    }

    @Test
    public void getDataFromFile_validFile_validResult() throws Exception {
        LoanBook dataFromFile = XmlUtil.getDataFromFile(VALID_FILE, XmlSerializableLoanBook.class).toModelType();
        assertEquals(9, dataFromFile.getLoanList().size());
    }

    @Test
    public void xmlAdaptedLoanFromFile_fileWithMissingLoanField_validResult() throws Exception {
        XmlAdaptedLoan actualLoan = XmlUtil.getDataFromFile(
            MISSING_LOAN_FIELD_FILE, XmlAdaptedLoanWithRootElement.class);
        XmlAdaptedLoan expectedLoan = new XmlAdaptedLoan(null,
                VALID_NRIC,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_BIKE,
                VALID_LOANRATE,
                VALID_LOANTIMEA,
                VALID_LOANTIMEB,
                VALID_LOANSTATUS,
                VALID_TAGS);
        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void xmlAdaptedLoanFromFile_fileWithInvalidLoanField_validResult() throws Exception {
        XmlAdaptedLoan actualLoan = XmlUtil.getDataFromFile(
            INVALID_LOAN_FIELD_FILE, XmlAdaptedLoanWithRootElement.class);
        XmlAdaptedLoan expectedLoan = new XmlAdaptedLoan(VALID_NAME,
                VALID_NRIC,
                INVALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_BIKE,
                VALID_LOANRATE,
                VALID_LOANTIMEA,
                VALID_LOANTIMEB,
                VALID_LOANSTATUS,
                VALID_TAGS);
        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void xmlAdaptedLoanFromFile_fileWithValidLoan_validResult() throws Exception {
        XmlAdaptedLoan actualLoan = XmlUtil.getDataFromFile(
            VALID_LOAN_FILE, XmlAdaptedLoanWithRootElement.class);
        XmlAdaptedLoan expectedLoan = new XmlAdaptedLoan(VALID_NAME,
                VALID_NRIC,
                VALID_PHONE,
                VALID_EMAIL,
                VALID_ADDRESS,
                VALID_BIKE,
                VALID_LOANRATE,
                VALID_LOANTIMEA,
                VALID_LOANTIMEB,
                VALID_LOANSTATUS,
                VALID_TAGS);
        assertEquals(expectedLoan, actualLoan);
    }

    @Test
    public void saveDataToFile_nullFile_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(null, new LoanBook());
    }

    @Test
    public void saveDataToFile_nullClass_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        XmlUtil.saveDataToFile(VALID_FILE, null);
    }

    @Test
    public void saveDataToFile_missingFile_fileNotFoundException() throws Exception {
        thrown.expect(FileNotFoundException.class);
        XmlUtil.saveDataToFile(MISSING_FILE, new LoanBook());
    }

    @Test
    public void saveDataToFile_validFile_dataSaved() throws Exception {
        FileUtil.createFile(TEMP_FILE);
        XmlSerializableLoanBook dataToWrite = new XmlSerializableLoanBook(new LoanBook());
        XmlUtil.saveDataToFile(TEMP_FILE, dataToWrite);
        XmlSerializableLoanBook dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLoanBook.class);
        assertEquals(dataToWrite, dataFromFile);

        LoanBookBuilder builder = new LoanBookBuilder(new LoanBook());
        dataToWrite = new XmlSerializableLoanBook(
                builder.withLoan(new LoanBuilder().build()).build());

        XmlUtil.saveDataToFile(TEMP_FILE,
            dataToWrite);
        dataFromFile = XmlUtil.getDataFromFile(TEMP_FILE, XmlSerializableLoanBook.class);
        assertEquals(dataToWrite, dataFromFile);
    }


    /**
     * Test class annotated with {@code XmlRootElement} to allow unmarshalling of .xml data to {@code XmlAdaptedLoan}
     * objects.
     */

    @XmlRootElement(name = "loan")
    private static class XmlAdaptedLoanWithRootElement extends XmlAdaptedLoan {}
}

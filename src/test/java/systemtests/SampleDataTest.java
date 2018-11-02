package systemtests;

import static loanbook.ui.testutil.GuiTestAssert.assertListMatching;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

import loanbook.model.LoanBook;
import loanbook.model.loan.Loan;
import loanbook.model.util.SampleDataUtil;
import loanbook.testutil.TestUtil;

public class SampleDataTest extends LoanBookSystemTest {
    /**
     * Returns null to force test app to load data of the file in {@code getDataFileLocation()}.
     */
    @Override
    protected LoanBook getInitialData() {
        return null;
    }

    /**
     * Returns a non-existent file location to force test app to load sample data.
     */
    @Override
    protected Path getDataFileLocation() {
        Path filePath = TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
        deleteFileIfExists(filePath);
        return filePath;
    }

    /**
     * Deletes the file at {@code filePath} if it exists.
     */
    private void deleteFileIfExists(Path filePath) {
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException ioe) {
            throw new AssertionError(ioe);
        }
    }

    @Test
    public void loanBook_dataFileDoesNotExist_loadSampleData() {
        Loan[] expectedList = SampleDataUtil.getSampleLoans();
        assertListMatching(getLoanListPanel(), expectedList);
    }
}

package loanbook.storage;

import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import loanbook.commons.events.model.LoanBookChangedEvent;
import loanbook.commons.events.storage.DataSavingExceptionEvent;
import loanbook.model.LoanBook;
import loanbook.model.ReadOnlyLoanBook;
import loanbook.model.UserPrefs;
import loanbook.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlLoanBookStorage loanBookStorage = new XmlLoanBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(loanBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void loanBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlLoanBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlLoanBookStorageTest} class.
         */
        LoanBook original = getTypicalLoanBook();
        storageManager.saveLoanBook(original);
        ReadOnlyLoanBook retrieved = storageManager.readLoanBook().get();
        assertEquals(original, new LoanBook(retrieved));
    }

    @Test
    public void getLoanBookFilePath() {
        assertNotNull(storageManager.getLoanBookFilePath());
    }

    @Test
    public void handleLoanBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlLoanBookStorageExceptionThrowingStub(Paths.get("dummy")),
                                             new JsonUserPrefsStorage(Paths.get("dummy")));
        storage.handleLoanBookChangedEvent(new LoanBookChangedEvent(new LoanBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlLoanBookStorageExceptionThrowingStub extends XmlLoanBookStorage {

        public XmlLoanBookStorageExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveLoanBook(ReadOnlyLoanBook loanBook, Path filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}

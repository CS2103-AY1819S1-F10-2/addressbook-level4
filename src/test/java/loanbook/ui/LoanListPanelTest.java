package loanbook.ui;

import static java.time.Duration.ofMillis;
import static loanbook.testutil.EventsUtil.postNow;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.testutil.TypicalLoans.getTypicalLoans;
import static loanbook.ui.testutil.GuiTestAssert.assertCardDisplaysLoan;
import static loanbook.ui.testutil.GuiTestAssert.assertCardEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.LoanCardHandle;
import guitests.guihandles.LoanListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loanbook.commons.events.ui.JumpToListRequestEvent;
import loanbook.commons.util.FileUtil;
import loanbook.commons.util.XmlUtil;
import loanbook.model.loan.Loan;
import loanbook.storage.XmlSerializableLoanBook;

public class LoanListPanelTest extends GuiUnitTest {
    private static final ObservableList<Loan> TYPICAL_LOANS =
            FXCollections.observableList(getTypicalLoans());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_LOAN);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private LoanListPanelHandle loanListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_LOANS);

        for (int i = 0; i < TYPICAL_LOANS.size(); i++) {
            loanListPanelHandle.navigateToCard(TYPICAL_LOANS.get(i));
            Loan expectedLoan = TYPICAL_LOANS.get(i);
            LoanCardHandle actualCard = loanListPanelHandle.getLoanCardHandle(i);

            assertCardDisplaysLoan(expectedLoan, actualCard);
            assertEquals(Integer.toString(i + 1), actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_LOANS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        LoanCardHandle expectedLoan = loanListPanelHandle.getLoanCardHandle(INDEX_SECOND_LOAN.getZeroBased());
        LoanCardHandle selectedLoan = loanListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedLoan, selectedLoan);
    }

    /**
     * Verifies that creating and deleting large number of loans in {@code LoanListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Loan> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of loan cards exceeded time limit");
    }

    /**
     * Returns a list of loans containing {@code loanCount} loans that is used to populate the
     * {@code LoanListPanel}.
     */
    private ObservableList<Loan> createBackingList(int loanCount) throws Exception {
        Path xmlFile = createXmlFileWithLoans(loanCount);
        XmlSerializableLoanBook xmlLoanBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableLoanBook.class);
        return FXCollections.observableArrayList(xmlLoanBook.toModelType().getLoanList());
    }

    /**
     * Returns a .xml file containing {@code loanCount} loans. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithLoans(int loanCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<loanbook>\n");
        for (int i = 0; i < loanCount; i++) {
            builder.append("<loans>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<nric>S9249123D</nric>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<loanStatus>ONGOING</loanStatus>\n");
            builder.append("<bike>b</bike>\n");
            builder.append("<rate>1</rate>\n");
            builder.append("<startTime>12:00</startTime>\n");
            builder.append("<endTime>12:00</endTime>\n");
            builder.append("</loans>\n");
        }
        builder.append("</loanbook>\n");

        Path manyLoansFile = Paths.get(TEST_DATA_FOLDER + "manyLoans.xml");
        FileUtil.createFile(manyLoansFile);
        FileUtil.writeToFile(manyLoansFile, builder.toString());
        manyLoansFile.toFile().deleteOnExit();
        return manyLoansFile;
    }

    /**
     * Initializes {@code loanListPanelHandle} with a {@code LoanListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code LoanListPanel}.
     */
    private void initUi(ObservableList<Loan> backingList) {
        LoanListPanel loanListPanel = new LoanListPanel(backingList);
        uiPartRule.setUiPart(loanListPanel);

        loanListPanelHandle = new LoanListPanelHandle(getChildNode(loanListPanel.getRoot(),
                LoanListPanelHandle.LOAN_LIST_VIEW_ID));
    }
}

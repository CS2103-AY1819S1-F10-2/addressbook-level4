package loanbook.ui;

import static java.time.Duration.ofMillis;
import static loanbook.testutil.EventsUtil.postNow;
import static loanbook.testutil.TypicalBikes.getTypicalBikes;
import static loanbook.testutil.TypicalIndexes.INDEX_SECOND_LOAN;
import static loanbook.ui.testutil.GuiTestAssert.assertBikeCardEquals;
import static loanbook.ui.testutil.GuiTestAssert.assertCardDisplaysBike;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.BikeCardHandle;
import guitests.guihandles.BikeListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import loanbook.commons.events.ui.JumpToListRequestEvent;
import loanbook.commons.util.FileUtil;
import loanbook.commons.util.XmlUtil;
import loanbook.model.bike.Bike;
import loanbook.storage.XmlSerializableLoanBook;

public class BikeListPanelTest extends GuiUnitTest {
    private static final ObservableList<Bike> TYPICAL_BIKES =
        FXCollections.observableList(getTypicalBikes());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_LOAN);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private BikeListPanelHandle bikeListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_BIKES);

        for (int i = 0; i < TYPICAL_BIKES.size(); i++) {
            bikeListPanelHandle.navigateToCard(TYPICAL_BIKES.get(i));
            Bike expectedBike = TYPICAL_BIKES.get(i);
            BikeCardHandle actualCard = bikeListPanelHandle.getCardHandle(i);

            assertCardDisplaysBike(expectedBike, actualCard);
            assertEquals(Integer.toString(i + 1), actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_BIKES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        BikeCardHandle expectedBike = bikeListPanelHandle.getCardHandle(INDEX_SECOND_LOAN.getZeroBased());
        BikeCardHandle selectedBike = bikeListPanelHandle.getHandleToSelectedCard();
        assertBikeCardEquals(expectedBike, selectedBike);
    }

    /**
     * Verifies that creating and deleting large number of bikes in {@code BikeListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Bike> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of bike cards exceeded time limit");
    }

    /**
     * Returns a list of bikes containing {@code bikeCount} bikes that is used to populate the
     * {@code BikeListPanel}.
     */
    private ObservableList<Bike> createBackingList(int bikeCount) throws Exception {
        Path xmlFile = createXmlFileWithBikes(bikeCount);
        XmlSerializableLoanBook xmlLoanBook =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableLoanBook.class);
        return FXCollections.observableArrayList(xmlLoanBook.toModelType().getBikeList());
    }

    /**
     * Returns a .xml file containing {@code bikeCount} bikes. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithBikes(int bikeCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<loanbook>\n");
        for (int i = 0; i < bikeCount; i++) {
            builder.append("<bikes>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("</bikes>\n");
        }
        builder.append("</loanbook>\n");

        Path manyBikesFile = Paths.get(TEST_DATA_FOLDER + "/manyBikes.xml");
        FileUtil.createFile(manyBikesFile);
        FileUtil.writeToFile(manyBikesFile, builder.toString());
        manyBikesFile.toFile().deleteOnExit();
        return manyBikesFile;
    }

    /**
     * Initializes {@code bikeListPanelHandle} with a {@code BikeListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code BikeListPanel}.
     */
    private void initUi(ObservableList<Bike> backingList) {
        BikeListPanel bikeListPanel = new BikeListPanel(backingList);
        uiPartRule.setUiPart(bikeListPanel);

        bikeListPanelHandle = new BikeListPanelHandle(getChildNode(bikeListPanel.getRoot(),
            BikeListPanelHandle.LIST_VIEW_ID));
    }
}

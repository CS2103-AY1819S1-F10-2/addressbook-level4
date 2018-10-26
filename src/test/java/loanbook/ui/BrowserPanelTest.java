package loanbook.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static loanbook.testutil.EventsUtil.postNow;
import static loanbook.testutil.TypicalLoans.ALICE;
import static loanbook.ui.BrowserPanel.DEFAULT_PAGE;
import static loanbook.ui.UiPart.FXML_FILE_FOLDER;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import loanbook.MainApp;
import loanbook.commons.events.ui.LoanPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private LoanPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new LoanPanelSelectionChangedEvent(ALICE);

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a loan
        postNow(selectionChangedEventStub);
        URL expectedLoanUrl = new URL(BrowserPanel.SEARCH_PAGE_URL + ALICE.getName().value.replaceAll(" ", "%20"));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedLoanUrl, browserPanelHandle.getLoadedUrl());
    }
}

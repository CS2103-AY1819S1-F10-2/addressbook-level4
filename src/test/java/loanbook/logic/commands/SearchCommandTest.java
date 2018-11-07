package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.LoanTime;

public class SearchCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LoanTime firstStartDate = LoanTime.startOfDayLoanTime("2000-01-01");
    private LoanTime secondStartDate = LoanTime.startOfDayLoanTime("2000-02-02");
    private LoanTime firstEndDate = LoanTime.endOfDayLoanTime("2000-03-03");
    private LoanTime secondEndDate = LoanTime.endOfDayLoanTime("2018-04-04");

    @Test
    public void equals() {

        SearchCommand firstSearch = new SearchCommand(firstStartDate, firstEndDate);

        // same object -> returns true
        assertTrue(firstSearch.equals(firstSearch));

        // same values -> returns true
        SearchCommand firstSearchCopy = new SearchCommand(firstStartDate, firstEndDate);
        assertTrue(firstSearch.equals(firstSearchCopy));

        // different types -> returns false
        assertFalse(firstSearch.equals(1));

        // null -> returns false
        assertFalse(firstSearch == null);

        // different start date -> returns false
        SearchCommand differentStartDateSearch = new SearchCommand(secondStartDate, firstEndDate);
        assertFalse(firstSearch.equals(differentStartDateSearch));

        // different end date -> returns false
        SearchCommand differentEndDateSearch = new SearchCommand(firstStartDate, secondEndDate);
        assertFalse(firstSearch.equals(differentEndDateSearch));

        // different start date and end date -> returns false
        SearchCommand differentStartAndEndDateSearch = new SearchCommand(secondStartDate, secondEndDate);
        assertFalse(firstSearch.equals(differentStartAndEndDateSearch));
    }

    @Test
    public void execute_noLoanFound_throwsCommandException() throws Exception {
        SearchCommand searchCommand = new SearchCommand(firstStartDate, firstEndDate);

        thrown.expect(CommandException.class);
        thrown.expectMessage(String.format(SearchCommand.MESSAGE_FAILURE, firstStartDate, firstEndDate));
        searchCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_loanFound_success() {
        SearchCommand searchCommand = new SearchCommand(firstStartDate, secondEndDate);
        assertCommandSuccess(searchCommand, model, commandHistory,
                String.format(SearchCommand.MESSAGE_SUCCESS, firstStartDate, secondEndDate), model);
    }
}

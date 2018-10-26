package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyLoanBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitLoanBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyLoanBook_success() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalLoanBook(), new UserPrefs());
        expectedModel.resetLoans();
        expectedModel.commitLoanBook();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

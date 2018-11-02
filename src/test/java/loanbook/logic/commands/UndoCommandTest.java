package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.logic.commands.CommandTestUtil.deleteFirstLoan;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Before;
import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstLoan(model);
        deleteFirstLoan(model);

        deleteFirstLoan(expectedModel);
        deleteFirstLoan(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoLoanBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoLoanBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}

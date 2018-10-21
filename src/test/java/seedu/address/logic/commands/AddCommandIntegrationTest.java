package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalLoans.getTypicalLoanBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.LoanBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    }

    @Test
    public void execute_newLoan_success() {
        Loan validLoan = new LoanBuilder().build();

        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
        expectedModel.addLoan(validLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(new AddCommand(validLoan), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validLoan), expectedModel);
    }

    @Test
    public void execute_duplicateLoan_throwsCommandException() {
        Loan loanInList = model.getLoanBook().getLoanList().get(0);
        assertCommandFailure(new AddCommand(loanInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_LOAN);
    }

}

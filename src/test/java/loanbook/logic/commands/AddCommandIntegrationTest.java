package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Before;
import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Loan;
import loanbook.testutil.LoanBuilder;

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
        Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());

        // This will increment the internal Loan Manager's counter.
        String expectedLoanId = expectedModel.getNextAvailableId().toString();

        Loan validLoan = new LoanBuilder()
                .withLoanId(expectedLoanId)
                .build();

        expectedModel.addLoan(validLoan);
        expectedModel.commitLoanBook();

        assertCommandSuccess(new AddCommand(validLoan), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validLoan), expectedModel);
    }


}

package loanbook.logic.commands;

import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL1;
import static loanbook.logic.commands.CommandTestUtil.VALID_USER_EMAIL2;
import static loanbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static loanbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;

import org.junit.Test;

import loanbook.logic.CommandHistory;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.model.loan.Email;

public class CheckEmailCommandTest {

    private final Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(model.getLoanBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute() {
        // The user has not set his email
        assertCommandFailure(new CheckEmailCommand(), model, commandHistory, CheckEmailCommand.MESSAGE_FAILURE);

        // The user set his email
        model.setMyEmail(VALID_USER_EMAIL1);
        String myEmail = new Email(VALID_USER_EMAIL1).getCensored();
        assertCommandSuccess(new CheckEmailCommand(),
                model, commandHistory, String.format(CheckEmailCommand.MESSAGE_SUCCESS, myEmail), expectedModel);

        // The user set another email
        model.setMyEmail(VALID_USER_EMAIL2);
        myEmail = new Email(VALID_USER_EMAIL2).getCensored();
        assertCommandSuccess(new CheckEmailCommand(),
                model, commandHistory, String.format(CheckEmailCommand.MESSAGE_SUCCESS, myEmail), expectedModel);
    }
}

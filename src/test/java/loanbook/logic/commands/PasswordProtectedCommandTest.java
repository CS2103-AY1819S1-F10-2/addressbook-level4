package loanbook.logic.commands;

import static loanbook.testutil.TypicalLoanBook.getTypicalLoanBook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.ModelManager;
import loanbook.model.UserPrefs;
import loanbook.testutil.Assert;

public class PasswordProtectedCommandTest {

    @Test
    public void assertCorrectPassword_correctAndIncorrectPassword_throwsCommandException() {
        Model model = new ModelManager(getTypicalLoanBook(), new UserPrefs());

        String expectedCorrectPassword = "a12345";
        String expectedIncorrectPassword = "incorrect";

        PasswordProtectedCommandStub correctStub = new PasswordProtectedCommandStub(expectedCorrectPassword);
        PasswordProtectedCommandStub incorrectStub = new PasswordProtectedCommandStub(expectedIncorrectPassword);

        try {
            correctStub.assertCorrectPassword(model);
        } catch (CommandException e) {
            throw new AssertionError("A CommandException has been thrown when it should not have.");
        }

        Assert.assertThrows(CommandException.class, Messages.MESSAGE_INVALID_PASSWORD, () ->
                incorrectStub.assertCorrectPassword(model));
    }

    @Test
    public void equalityTest() {
        PasswordProtectedCommandStub command1 = new PasswordProtectedCommandStub("coverage");
        PasswordProtectedCommandStub command2 = new PasswordProtectedCommandStub("coverage");
        PasswordProtectedCommandStub command3 = new PasswordProtectedCommandStub("galore");

        assertEquals(command1, command1); // Same instance
        assertEquals(command1, command2); // Same password
        assertNotEquals(command1, command3); // Different passwords
        assertNotEquals(command1, "Not a command"); // Different types
    }

    private static class PasswordProtectedCommandStub extends PasswordProtectedCommand {
        PasswordProtectedCommandStub(String password) {
            super(password, "TEST_COMMAND");
        }

        @Override
        public CommandResult execute(Model model, CommandHistory history) {
            throw new AssertionError("This method should not be called.");
        }
    }
}

package loanbook.logic.commands;

import loanbook.commons.core.Messages;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.Password;

/**
 * Represents a Command that requires password authentication.
 */
public abstract class PasswordProtectedCommand extends Command {

    private final Password targetPassword;

    public PasswordProtectedCommand(Password password) {
        targetPassword = password;
    }

    /**
     * Asserts that the password provided matches that of the specified model's.
     *
     * @throws CommandException if the password does not match the specified model's
     */
    protected void assertCorrectPassword(Model model) throws CommandException {
        if (!Password.isSamePassword(model.getPass(), targetPassword)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PASSWORD);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PasswordProtectedCommand
                && targetPassword.equals(((PasswordProtectedCommand) other).targetPassword));
    }
}

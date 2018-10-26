package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.Password;

/**
 * Set password to loan book.
 */
public class SetPasswordCommand extends Command {

    public static final String COMMAND_WORD = "setpass";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change the password of the loanbook.\n"
            + "Parameters: OLDPASS NEWPASS\n"
            + "Example: " + COMMAND_WORD + " a12345 12345678";

    public static final String MESSAGE_CHANGE_PASSWORD_SUCCESS = "Password successfully changed!";

    private Password oldPassInput;
    private Password newPassInput;

    public SetPasswordCommand(Password oldPassInput, Password newPassInput) {
        requireNonNull(oldPassInput);
        requireNonNull(newPassInput);
        this.oldPassInput = oldPassInput;
        this.newPassInput = newPassInput;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!Password.isSamePassword(model.getPass(), oldPassInput)) {
            throw new CommandException(Messages.MESSAGE_INVALID_OLD_PASS);
        }
        if (Password.isSamePassword(model.getPass(), newPassInput)) {
            throw new CommandException(Messages.MESSAGE_SAME_AS_CURRENT_PASSWORD);
        }
        model.setPass(newPassInput);
        return new CommandResult(MESSAGE_CHANGE_PASSWORD_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetPasswordCommand // instanceof handles nulls
                && newPassInput.equals(((SetPasswordCommand) other).newPassInput)
                && oldPassInput.equals(((SetPasswordCommand) other).oldPassInput)); // state check
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;
import static loanbook.commons.util.CollectionUtil.requireAllNonNull;

import loanbook.commons.core.Messages;
import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Email;

/**
 * Set user's email to the app.
 */
public class SetEmailCommand extends Command {

    public static final String COMMAND_WORD = "setemail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set your email to the app. "
            + "Please note that the default email is <default>!\n"
            + "Parameter: OLDEMAIL NEWEMAIL\n"
            + "Example: " + COMMAND_WORD + " default myemail@gmail.com";

    public static final String MESSAGE_SUCCESS = "Your email is set successfully!";

    private final String oldEmail;
    private final String newEmail;

    /**
     * Creates an SetEmailCommand to set user's {@code Email} according to the {@code newEmail} provided.
     */
    public SetEmailCommand(String oldEmail, String newEmail) {
        requireAllNonNull(oldEmail, newEmail);
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!oldEmail.equals(model.getMyEmail())) {
            throw new CommandException(Messages.MESSAGE_WRONG_OLDEMAIL);
        }

        if (newEmail.equals(oldEmail)) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_FAILURE);
        }

        if (!Email.isValidGmail(newEmail)) {
            throw new CommandException(Messages.MESSAGE_INVALID_EMAIL);
        }

        model.setMyEmail(newEmail);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetEmailCommand // instanceof handles nulls
                && newEmail.equals(((SetEmailCommand) other).newEmail)
                && oldEmail.equals(((SetEmailCommand) other).oldEmail)); // state check
    }
}

package loanbook.logic.commands;

import static java.util.Objects.requireNonNull;

import loanbook.logic.CommandHistory;
import loanbook.logic.commands.exceptions.CommandException;
import loanbook.model.Model;
import loanbook.model.loan.Email;

/**
 * Get user's current email address.
 */
public class CheckEmailCommand extends Command {

    public static final String COMMAND_WORD = "checkemail";
    public static final String MESSAGE_SUCCESS = "Your email is %s.";
    public static final String MESSAGE_FAILURE = "You have not set your email yet!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        String myEmail = model.getMyEmail();

        if (myEmail.equals("default")) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        Email email = new Email(myEmail);
        return new CommandResult(String.format(MESSAGE_SUCCESS, email.getCensored()));
    }
}

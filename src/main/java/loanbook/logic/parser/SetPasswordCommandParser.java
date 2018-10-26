package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import loanbook.logic.commands.SetPasswordCommand;
import loanbook.logic.parser.exceptions.ParseException;
import loanbook.model.Password;

/**
 * Parses input arguments and creates a new SetPasswordCommand object
 */
public class SetPasswordCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPasswordCommand parse(String args) throws ParseException {
        String[] parts = args.trim().split(" ");
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }

        String oldPass = parts[0];
        String newPass = parts[1];

        if (!Password.isValidPass(newPass)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINTS);
        }
        Password oldPassInput = new Password(oldPass);
        Password newPassInput = new Password(newPass);

        return new SetPasswordCommand(oldPassInput, newPassInput);
    }
}

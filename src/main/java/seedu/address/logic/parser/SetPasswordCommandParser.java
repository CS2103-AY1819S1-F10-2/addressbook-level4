package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.SetPasswordCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Password;

public class SetPasswordCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetPasswordCommand parse(String args) throws ParseException{
        String[] parts = args.trim().split(" ");
        if (parts.length != 2) {
            System.out.println(parts[0] + " | " + parts[1] + " | " + parts[2]);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetPasswordCommand.MESSAGE_USAGE));
        }
        Password oldPassInput = new Password(parts[0]);
        Password newPassInput = new Password(parts[1]);
        return new SetPasswordCommand(oldPassInput, newPassInput);
    }
}

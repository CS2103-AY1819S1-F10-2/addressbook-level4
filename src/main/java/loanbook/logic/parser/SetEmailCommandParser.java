package loanbook.logic.parser;

import static loanbook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import loanbook.logic.commands.SetEmailCommand;
import loanbook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SetEmailCommand object.
 */
public class SetEmailCommandParser implements Parser<SetEmailCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetEmailCommand
     * and returns an SetEmailCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetEmailCommand parse(String args) throws ParseException {
        String[] parts = args.trim().split(" ");

        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetEmailCommand.MESSAGE_USAGE));
        }

        String oldEmail = parts[0];
        String newEmail = parts[1];

        return new SetEmailCommand(oldEmail, newEmail);
    }
}

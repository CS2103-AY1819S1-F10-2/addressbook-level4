package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PASSWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PASSWORD;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static String PASSWORD = "a12345"; // Currently hard-coded. To add command that allows setting of password.

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_PASSWORD);
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_PASSWORD)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (!PASSWORD.equals(argMultimap.getValue(PREFIX_PASSWORD).get())) {
            throw new ParseException(String.format(MESSAGE_INVALID_PASSWORD, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get()));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
